package flickrBackup.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import flickrBackup.model.Argazkia;
import flickrBackup.model.Argazkia.Pribatutasuna;
import flickrBackup.model.Nagusia;

public class Igotzekoa extends JPanel {
	
//	private JLabel aukeratu;
	private File karpeta;
	private JTable taula;
	private IgotzekoaJT model;
	
	public void sortu(){
		this.setLayout(new BorderLayout());

		
		JButton ireki = new JButton("IREKI");
		this.add(ireki, BorderLayout.NORTH);
		ireki.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int aukera = chooser.showOpenDialog(Igotzekoa.this);
				 if (aukera == JFileChooser.APPROVE_OPTION) {
						karpeta = chooser.getSelectedFile();
//						System.out.println(karpeta);
//						 Igotzekoa.this.pantailaratuArgazkiak();
						 Igotzekoa.this.argazkiakLortu();
						 ((JFrame) SwingUtilities.getWindowAncestor(Igotzekoa.this)).pack();
			      } 

			}
		});
		
		JPanel behekoJP = new JPanel(new BorderLayout());
		
		
		JPanel aldaketaJP = new JPanel(new FlowLayout());
		behekoJP.add(aldaketaJP, BorderLayout.CENTER);
//		aldaketaJP.setMaximumSize(new Dimension(arg0, arg1));
		JLabel deskL = new JLabel("Deskribapena");
		JTextArea deskT = new JTextArea(3,30);
		JScrollPane deskSP = new JScrollPane(deskT);
		

		JLabel etikL = new JLabel("Etiketak");
		JTextArea etikT = new JTextArea(3,30);
		JScrollPane etikSP = new JScrollPane(etikT);
		
		JLabel pribL = new JLabel("Pribatutasuna");
		JComboBox<Pribatutasuna> comboBox = new JComboBox<Pribatutasuna>();
		for (Pribatutasuna elem : Pribatutasuna.values()) {
			comboBox.addItem(elem);
		}
		
		aldaketaJP.add(deskL);
		aldaketaJP.add(deskSP);
		aldaketaJP.add(etikL);
		aldaketaJP.add(etikSP);
		aldaketaJP.add(pribL);
		aldaketaJP.add(comboBox);
		
		this.add(behekoJP, BorderLayout.SOUTH);
		
		JButton gorde = new JButton("Gorde");
		behekoJP.add(gorde, BorderLayout.SOUTH);
		gorde.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int index : taula.getSelectedRows()) {
					if (!deskT.getText().equals("")){
						taula.setValueAt(deskT.getText(), index, 2);
					}
					if (!etikT.getText().equals("")){
						ArrayList<String> list = new ArrayList<String>();
						for (String iterable_element : etikT.getText().split("[\\r\\n]+")) {
							list.add(iterable_element);
						};
						taula.setValueAt(list, index, 3);
					}
					if(comboBox.getSelectedItem()!=null){
						taula.setValueAt(comboBox.getSelectedItem(), index, 5);
					}
				}
				comboBox.setSelectedItem(null);
				model.fireTableStructureChanged();
			}
		});
		
		
	}
	
	
	public void argazkiakLortu(){
		Nagusia nL = Nagusia.getInstantzia();
		ArrayList<Argazkia> igotzeko = nL.igotzekoArgazkiakLortu(karpeta);
		model = new IgotzekoaJT(igotzeko);
		taula = new JTable(model);
		taula.setRowHeight(60);
		taula.setRowMargin(5);
		JScrollPane tableContainer = new JScrollPane(taula);
		this.add(tableContainer, BorderLayout.CENTER);

	}
	
//	public void pantailaratuArgazkiak(){
//		for (File f : karpeta.listFiles(Nagusia.IMAGE_FILTER)){
//			System.out.println(f);
//			try {
//				Image img = ImageIO.read(f).getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);
//				ImageIcon icon = new ImageIcon(img);
//				JLabel label = new JLabel();
//				label.setIcon(icon);
//				nagusia.add(label);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//           
//         }
//	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Igotzekoa n = new Igotzekoa();
		n.sortu();
		JFrame f = new JFrame();
		f.setContentPane(n);
		f.setVisible(true);
		f.pack();
	}
	
}
