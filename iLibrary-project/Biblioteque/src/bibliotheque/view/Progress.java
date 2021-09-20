package bibliotheque.view;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import bibliotheque.model.Utilisateur;

import java.awt.Color;



public class Progress extends JFrame {

	/**
	 * Create the panel.
	 */
	Thread t;
	JProgressBar progressBar;
	Utilisateur user;
	public Progress(Utilisateur user) {
		this.user = user;
		getContentPane().setLayout(null);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));
		setBounds(0, 0, 870, 450);
		
		t =  new Thread(new Traitement());
		 progressBar = new JProgressBar();
		progressBar.setBounds(17, 410, 833, 20);
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		getContentPane().add(progressBar);
		t.start();
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(this.getClass().getResource("/login/LibProgress.png")));
		lblNewLabel.setBounds(10, 0, 870, 450);
		getContentPane().add(lblNewLabel);
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	public class Traitement implements Runnable{
		/**
		 * statically waiting for a while after the main library window shows.
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int val=0;val<150;val++)
			{
				progressBar.setValue(val);
				try {
				t.sleep(100);
				} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				}
			}

			Librairie lib = new Librairie(user);
			lib.getFrame().setVisible(true);
			
			setVisible(false);
		}
		
	}
		
}
