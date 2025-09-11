package visualizacao;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Janela Swing para visualizar a animação do preenchimento frame a frame.
 */
public class VisualizadorAnimacao {
    private final List<BufferedImage> frames;
    private Timer animationTimer;
    private int currentFrame = 0;
    private boolean isPlaying = false;
    private final JFrame frame;
    private final JLabel imageLabel;
    private final JSlider slider;
    private final JButton playButton;
    private final JLabel frameCountLabel;

    public VisualizadorAnimacao(List<BufferedImage> frames) {
        this.frames = frames;
        frame = new JFrame("Visualizador de Animação - Flood Fill");
        imageLabel = new JLabel();
        slider = new JSlider(0, Math.max(0, frames.size() - 1), 0);
        playButton = new JButton("▶ Play");
        frameCountLabel = new JLabel();
        initializeUI();
    }

    private void initializeUI() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // DISPOSE_ON_CLOSE para não fechar o programa
                                                                 // principal
        frame.setLayout(new BorderLayout(10, 10));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Centraliza na tela

        // Painel da imagem
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Painel de controles
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Slider
        slider.addChangeListener(e -> {
            if (!slider.getValueIsAdjusting() && !isPlaying) {
                currentFrame = slider.getValue();
                updateFrame();
            }
        });
        controlPanel.add(slider, BorderLayout.NORTH);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playButton.addActionListener(e -> togglePlayPause());
        JButton firstButton = new JButton("⏮");
        firstButton.addActionListener(e -> goToFrame(0));
        JButton lastButton = new JButton("⏭");
        lastButton.addActionListener(e -> goToFrame(frames.size() - 1));

        buttonPanel.add(firstButton);
        buttonPanel.add(playButton);
        buttonPanel.add(lastButton);

        // Contador de frames
        frameCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(frameCountLabel, BorderLayout.SOUTH);
        controlPanel.add(bottomPanel, BorderLayout.CENTER);

        frame.add(controlPanel, BorderLayout.SOUTH);

        // Timer para o "play"
        animationTimer = new Timer(50, e -> {
            if (currentFrame < frames.size() - 1) {
                currentFrame++;
                updateFrame();
            } else {
                pause(); // Para no último frame
            }
        });

        updateFrame(); // Mostra o primeiro frame
    }

    private void updateFrame() {
        if (!frames.isEmpty()) {
            BufferedImage img = frames.get(currentFrame);
            // Redimensiona a imagem para melhor visualização se for pequena
            int w = img.getWidth();
            int h = img.getHeight();
            int scale = Math.min(600 / w, 500 / h);
            scale = Math.max(1, scale);
            Image scaledImg = img.getScaledInstance(w * scale, h * scale, Image.SCALE_REPLICATE);
            imageLabel.setIcon(new ImageIcon(scaledImg));

            slider.setValue(currentFrame);
            frameCountLabel.setText("Frame: " + (currentFrame + 1) + " / " + frames.size());
        }
    }

    private void togglePlayPause() {
        if (isPlaying) {
            pause();
        } else {
            play();
        }
    }

    private void play() {
        if (currentFrame == frames.size() - 1) {
            goToFrame(0); // Reinicia se estiver no final
        }
        isPlaying = true;
        playButton.setText("⏸ Pause");
        animationTimer.start();
    }

    private void pause() {
        isPlaying = false;
        playButton.setText("▶ Play");
        animationTimer.stop();
    }

    private void goToFrame(int frameIndex) {
        if (isPlaying)
            pause();
        currentFrame = frameIndex;
        updateFrame();
    }

    public void exibir() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}