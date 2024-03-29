package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.Controlador;
import modelo.ModeloConsultas;
import modelo.ModeloGestionDatos;

public class GestionProfesores extends JFrame {

	private Controlador controlador;
	private ModeloConsultas modeloConsultas;
	private ModeloGestionDatos modeloGestionDatos;
	private JPanel contentPane;
	private JTable tablaProfesores;
	private JTextField txtNumero;
	private JTextField txtNombre;
	private JTextField txtTitulacion;
	private JTextField txtDni;
	private JTextField txtRelacion_laboral;
	private JTextField txtTelefono1;
	private JPanel HeaderPanel;
	private JScrollPane scrollPane;
	private JLabel lblTitulo;
	private JLabel lblUemLogo;
	private JLabel lblPerfil;
	private JButton btnVolver;
	private JButton btnAI_profesor;
	private JButton btnModProfesor;
	private JLabel lblImportarActividades;
	private JComboBox comboBoxColumna;
	private JTextField txtBuscador;
	private String num;
	private String nombre;
	private String ape1;
	private String ape2;
	private String nombreSeparado[];
	private JButton btnAddProfesor;
	private JLabel lblLupa;
	private JLabel lblInfo;

	public GestionProfesores() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				controlador.solicitudDatosProfesores();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/ue.png"));
		setTitle("Hospital simulado");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(98, 145, 800, 500);
		contentPane.add(scrollPane);

		tablaProfesores = new JTable();
		tablaProfesores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (!tablaProfesores.isRowSelected(tablaProfesores.getSelectedRowCount())) {
					btnModProfesor.setEnabled(true);
					btnAI_profesor.setEnabled(true);
				}
				// tablaProfesores.isRowSelected(tablaProfesores.getSelectedRowCount()-1);
				// btnModProfesor.setEnabled(false);

			}
		});

		tablaProfesores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaProfesores.setRowHeight(30);
		tablaProfesores.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(tablaProfesores);

		HeaderPanel = new JPanel();
		HeaderPanel.setBackground(new Color(164, 44, 52));
		HeaderPanel.setBounds(0, 0, 1000, 100);
		contentPane.add(HeaderPanel);
		HeaderPanel.setLayout(null);

		lblTitulo = new JLabel("Profesores");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(0, 0, 1000, 100);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 50));
		HeaderPanel.add(lblTitulo);
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		lblTitulo.setVerticalAlignment(JLabel.CENTER);

		ImageIcon ueIcon = new ImageIcon("./img/ue.png");
		lblUemLogo = new JLabel(ueIcon);
		lblUemLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblUemLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				controlador.loginToHome();
			}

			@SuppressWarnings("deprecation")
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.HAND_CURSOR);
			}

			@SuppressWarnings("deprecation")
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.DEFAULT_CURSOR);
			}
		});
		lblUemLogo.setBounds(50, 0, 100, 100);
		HeaderPanel.add(lblUemLogo);

		ImageIcon perfilIcon = new ImageIcon("./img/usuario.png");
		lblPerfil = new JLabel(perfilIcon);
		lblPerfil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				controlador.gestionProfesoresToPerfil();
			}

			@SuppressWarnings("deprecation")
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.HAND_CURSOR);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.DEFAULT_CURSOR);
			}
		});
		lblPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerfil.setBounds(850, 0, 100, 100);
		HeaderPanel.add(lblPerfil);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				controlador.gestionProfesoresToGestion();
				deselecionarFilayBotones();
			}
		});
		btnVolver.setBounds(100, 685, 150, 40);
		contentPane.add(btnVolver);

		btnAI_profesor = new JButton("Activo/Inactivo");
		btnAI_profesor.setEnabled(false);
		btnAI_profesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				activoProfesor();
				controlador.solicitudBorrar(this);
			}
		});
		btnAI_profesor.setBounds(532, 685, 150, 40);
		contentPane.add(btnAI_profesor);

		btnModProfesor = new JButton("Modificar profesor");
		btnModProfesor.setEnabled(false);
		btnModProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if (tablaProfesores.getSelectedRowCount() > 0) {
					controlador.solicitudCamposDeTextoProfe();
					controlador.gestionProfesoresTogestionProsoresAddMod();
					deselecionarFilayBotones();
				}

			}
		});
		btnModProfesor.setBounds(316, 685, 150, 40);
		contentPane.add(btnModProfesor);

		btnAddProfesor = new JButton("A\u00F1adir profesor");
		btnAddProfesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				controlador.gestionProfesoresTogestionProsoresAddMod();
				deselecionarFilayBotones();
			}
		});
		btnAddProfesor.setBounds(748, 685, 150, 40);
		contentPane.add(btnAddProfesor);

		txtBuscador = new JTextField();
		txtBuscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtBuscador.setText("");
			}
		});
		txtBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!txtBuscador.getText().equals("")) {
					controlador.solicitudBuscador(this);
				} else {
					controlador.solicitudDatosUsuarios();
				}
			}
		});
		txtBuscador.setText("Buscador");
		txtBuscador.setHorizontalAlignment(SwingConstants.CENTER);
		txtBuscador.setBounds(728, 111, 140, 22);
		contentPane.add(txtBuscador);
		txtBuscador.setColumns(10);

		lblImportarActividades = new JLabel("Importar Profesores");
		lblImportarActividades.setIcon(
				new ImageIcon(GestionActividad.class.getResource("/javax/swing/plaf/basic/icons/JavaCup16.png")));
		lblImportarActividades.setBounds(98, 111, 124, 20);
		contentPane.add(lblImportarActividades);
		lblImportarActividades.setVisible(false);
		
		lblInfo = new JLabel("");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setBounds(0, 110, 1000, 30);
		contentPane.add(lblInfo);

		ImageIcon lupa = new ImageIcon("./img/buscar.png");
		lblLupa = new JLabel(lupa);
		lblLupa.setBounds(878, 111, 20, 22);
		contentPane.add(lblLupa);
	}
	// Setters

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void setModeloConsultas(ModeloConsultas modeloConsultas) {
		this.modeloConsultas = modeloConsultas;
	}

	public void setModeloGestionDatos(ModeloGestionDatos modeloGestionDatos) {
		this.modeloGestionDatos = modeloGestionDatos;
	}

	// Getters
	public String getNumGP() {
		num = String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 0));
		return num;

	}

	public void getNombreGP() {
		String nombreYapellidos = String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 1));
		nombreSeparado = nombreYapellidos.split(" ");
		this.nombre = nombreSeparado[0].trim();
		this.ape1 = nombreSeparado[1].trim();
		this.ape2 = nombreSeparado[2];

	}

	public String getNombreProfeGP() {
		getNombreGP();

		return nombre.trim();
	}

	public String getApe1GP() {

		return ape1.trim();
	}

	public String getApe2GP() {

		return ape2.trim();
	}

	public String getTitulacion() {
		return String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 2));
	}

	public String getDni() {
		return String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 3));
	}

	public String getActivo() {
		return String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 4));
	}

	public String getRelacion() {
		return String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 5));
	}

	public String getTlfn1() {
		return String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 6));
	}

	public String getTlfn2() {
		return String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 7));
	}

	public String getEmail1() {
		return String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 8));
	}

	public String getEmail2() {
		return String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 9));
	}

	public DefaultTableModel getModel() {
		return (DefaultTableModel) tablaProfesores.getModel();
	}

	public String getPalabraBuscador() {
		return txtBuscador.getText();
	}

	public String getPrimaryKey() {
		return String.valueOf(tablaProfesores.getValueAt(tablaProfesores.getSelectedRow(), 0));
	}

	//

	public void activoProfesor() {
		DefaultTableModel model = (DefaultTableModel) tablaProfesores.getModel();
		if (getNumGP().equals(String.valueOf(model.getValueAt(tablaProfesores.getSelectedRow(), 0)))) {
			 lblInfo.setText("Se ha cambiado estado del profesor");
			model.setValueAt(getNumGP(), tablaProfesores.getSelectedRow(), 0);
			if (getActivo().equals("0")) {
				model.setValueAt("1", tablaProfesores.getSelectedRow(), 4);
			} else {
				model.setValueAt("0", tablaProfesores.getSelectedRow(), 4);
			}
		} else {
			lblInfo.setText("No se ha cambiado estado del profesor");
		}

	}

	public void deselecionarFilayBotones() {
		tablaProfesores.isRowSelected(tablaProfesores.getSelectedRowCount() - 1);
		btnModProfesor.setEnabled(false);
		btnAI_profesor.setEnabled(false);
		lblInfo.setText("");
	}
}
