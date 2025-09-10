package visualizacao;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Visualizador de animação do Flood Fill
 * Permite ver o progresso do algoritmo frame a frame
 */
public class AnimationViewer {
    private List<BufferedImage> frames;
    private JFrame frame;
    private JLabel imageLabel;
    private JSlider slider;
    private JButton playButton;
    private JLabel frameCountLabel;
    private Timer animationTimer;
    private int currentFrame;
    private boolean isPlaying;
    
    public AnimationViewer(List<BufferedImage> frames) {
        this.frames = frames;
        this.currentFrame = 0;
        this.isPlaying = false;
        initializeUI();
    }
    
    private void initializeUI() {
        frame = new JFrame("Flood Fill - Visualizador de Animação");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // Painel para a imagem
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBackground(Color.DARK_GRAY);
        imageLabel.setOpaque(true);
        
        if (!frames.isEmpty()) {
            updateImage(0);
        }
        
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.setBackground(Color.DARK_GRAY);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Painel de controles
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(Color.LIGHT_GRAY);
        
        // Slider para navegar pelos frames
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        slider = new JSlider(0, Math.max(0, frames.size() - 1), 0);
        slider.setMajorTickSpacing(Math.max(1, frames.size() / 10));
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(e -> {
            if (!isPlaying) {
                currentFrame = slider.getValue();
                updateImage(currentFrame);
                updateFrameLabel();
            }
        });
        
        sliderPanel.add(slider, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        
        JButton firstButton = new JButton("⏮ Início");
        firstButton.addActionListener(e -> goToFrame(0));
        
        playButton = new JButton("▶️ Play");
        playButton.setPreferredSize(new Dimension(100, 30));
        playButton.addActionListener(e -> togglePlayPause());
        
        JButton stopButton = new JButton("⏹ Stop");
        stopButton.addActionListener(e -> stop());
        
        JButton lastButton = new JButton("Fim ⏭");
        lastButton.addActionListener(e -> goToFrame(frames.size() - 1));
        
        buttonPanel.add(firstButton);
        buttonPanel.add(playButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(lastButton);
        
        // Label para mostrar o frame atual
        frameCountLabel = new JLabel("Frame: 1 / " + frames.size());
        frameCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        frameCountLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel framePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        framePanel.setBackground(Color.LIGHT_GRAY);
        framePanel.add(frameCountLabel);
        
        // Controle de velocidade
        JPanel speedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        speedPanel.setBackground(Color.LIGHT_GRAY);
        
        JLabel speedLabel = new JLabel("Velocidade (ms):");
        SpinnerNumberModel speedModel = new SpinnerNumberModel(50, 10, 1000, 10);
        JSpinner speedSpinner = new JSpinner(speedModel);
        speedSpinner.setPreferredSize(new Dimension(80, 25));
        speedSpinner.addChangeListener(e -> {
            if (animationTimer != null) {
                animationTimer.setDelay((int) speedSpinner.getValue());
            }
        });
        
        speedPanel.add(speedLabel);
        speedPanel.add(speedSpinner);
        
        // Adiciona todos os painéis ao painel de controle
        controlPanel.add(sliderPanel);
        controlPanel.add(buttonPanel);
        controlPanel.add(framePanel);
        controlPanel.add(speedPanel);
        
        frame.add(controlPanel, BorderLayout.SOUTH);
        
        // Timer para animação
        animationTimer = new Timer(50, e -> {
            if (isPlaying && currentFrame < frames.size() - 1) {
                currentFrame++;
                updateImage(currentFrame);
                slider.setValue(currentFrame);
                updateFrameLabel();
            } else if (currentFrame >= frames.size() - 1) {
                pause();
            }
        });
        
        // Configurações finais
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
    }
    
    private void updateImage(int frameIndex) {
        if (frameIndex >= 0 && frameIndex < frames.size()) {
            BufferedImage img = frames.get(frameIndex);
            
            // Escala a imagem se for muito pequena
            int scale = calculateScale(img);
            if (scale > 1) {
                img = scaleImage(img, scale);
            }
            
            imageLabel.setIcon(new ImageIcon(img));
        }
    }
    
    private int calculateScale(BufferedImage img) {
        int minSize = 500;
        int scale = 1;
        
        if (img.getWidth() < minSize && img.getHeight() < minSize) {
            scale = Math.min(minSize / img.getWidth(), minSize / img.getHeight());
        }
        
        return Math.min(scale, 15); // Máximo de 15x de zoom
    }
    
    private BufferedImage scaleImage(BufferedImage original, int scale) {
        int newWidth = original.getWidth() * scale;
        int newHeight = original.getHeight() * scale;
        
        BufferedImage scaled = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = scaled.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(original, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        
        return scaled;
    }
    
    private void updateFrameLabel() {
        frameCountLabel.setText("Frame: " + (currentFrame + 1) + " / " + frames.size());
    }
    
    private void togglePlayPause() {
        if (isPlaying) {
            pause();
        } else {
            play();
        }
    }
    
    private void play() {
        if (currentFrame >= frames.size() - 1) {
            currentFrame = 0;
            slider.setValue(0);
            updateImage(0);
        }
        isPlaying = true;
        playButton.setText("⏸ Pause");
        animationTimer.start();
    }
    
    private void pause() {
        isPlaying = false;
        playButton.setText("▶️ Play");
        animationTimer.stop();
    }
    
    private void stop() {
        isPlaying = false;
        playButton.setText("▶️ Play");
        animationTimer.stop();
        currentFrame = 0;
        slider.setValue(0);
        updateImage(0);
        updateFrameLabel();
    }
    
    private void goToFrame(int frame) {
        currentFrame = frame;
        slider.setValue(frame);
        updateImage(frame);
        updateFrameLabel();
        if (isPlaying) {
            pause();
        }
    }
    
    public void show() {
        frame.setVisible(true);
    }
}