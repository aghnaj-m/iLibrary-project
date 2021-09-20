package bibliotheque.qrcode;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import bibliotheque.dao.AdherentDao;
import bibliotheque.model.Utilisateur;
import bibliotheque.view.EmpruntDialog;
import bibliotheque.view.Progress;
import bibliotheque.view.iLivres;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JPanel;

public class QrScanner implements Runnable, ThreadFactory {

	private JFrame frame;
	private static final long serialVersionUID = 6441489157408381878L;

	private Executor executor = Executors.newSingleThreadExecutor(this);

	private Webcam webcam = null;
	private WebcamPanel panel = null;
	private iLivres parent = null;
	private EmpruntDialog parent2 = null;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QrScanner window = new QrScanner();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public QrScanner(iLivres signin) {
		this.parent = signin;
		initialize();
	}
	
	public QrScanner(EmpruntDialog empruntDialog)
	{
		parent2 = empruntDialog;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 355, 265);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0,0,0,0));

		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(QrScanner.class.getResource("/QrScanner/QrScanner.png")));
		label.setBounds(-72, 0, 455, 270);
		frame.getContentPane().add(label);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(159, 76, 171, 168);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				lblNewLabel.setVisible(false);
				panel.setVisible(true);
			}
		});
		frame.getContentPane().add(lblNewLabel);
		
		Dimension size = WebcamResolution.QVGA.getSize();

		webcam = Webcam.getWebcams().get(0);
		webcam.setViewSize(size);

		panel = new WebcamPanel(webcam);
		panel.setPreferredSize(size);
		panel.setFPSDisplayed(true);
		panel.setBounds(158, 73, 175, 173);
		panel.setVisible(false);
		
		
		frame.getContentPane().add(panel);
		
		executor.execute(this);

	}
	
	public void closeIt() { webcam.close();}
	public void openIt() { webcam.open();}
	public void visible(boolean b)
	{
		this.frame.setVisible(b);
	}
	
	@Override
	public void run() {
		
		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Result result = null;

			BufferedImage image = null;

			if (webcam.isOpen()) {

				if ((image = webcam.getImage()) == null) {
					continue;
				}

				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

				try {
					result = new MultiFormatReader().decode(bitmap);
				} catch (NotFoundException e) {
					// fall thru, it means there is no QR code in image
				}
			}

			if (result != null) {
				if(parent != null)
					authetication(result);
				else if(parent2 != null){
					parent2.getChampBeneficiaire().setText(result.getText().substring(32));
					System.gc();
					this.panel.setVisible(false);
					this.closeIt();
					this.frame.dispose();
				}
					
			}
			
		}while(true);

						
	}
	
	private void authetication(Result result)
	{
		AdherentDao dao = new AdherentDao();
		List<Utilisateur> users = dao.getAll();
		Utilisateur utilisateur = null;
		int exists = 0;
		for(Utilisateur user: users)
		{
			if ((user.getMot_de_pass()+user.getCne()).equals(result.getText()))
			{
				exists = 1;
				utilisateur = user;
			}
			
		}
		if (exists == 1) {
			//JOptionPane.showMessageDialog(null, "WELCOME BACK!");
			JFrame parentFrame = parent.getFrame();
			parentFrame.setVisible(false);
			parentFrame = null;
			System.gc();
			this.panel.setVisible(false);
			this.closeIt();
			this.frame.dispose();
			new Progress(utilisateur);
		}else {
			JOptionPane.showMessageDialog(null, "Qr code invalide.","Warning",JOptionPane.WARNING_MESSAGE);
			
		}
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "example-runner");
		t.setDaemon(true);
		return t;
	}
}
