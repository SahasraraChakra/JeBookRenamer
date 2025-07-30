/*
************************************************
    Author	:   Jeevan Kumar Vishwakarman
    Company	:   J Soft
    Created on	:   25-Jul-2025  12:26:49 pm
************************************************
 */
package EBookRenamerJ;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Jeevan Kumar Vishwakarman
 */
public class frmRenamer extends javax.swing.JFrame {

    /**
     * Creates new form frmRenamer
     */
    BufferedImage icon;
    DefaultTableModel dtm;
    Font customFont, boldFont;
    FontMetrics metrics;
    StringBuilder previewname;
    int editRow      = -1;
    JTextComponent topicEditor;
    Color defaultTableForeground;
    Color defaultTableBackground;
    Color defaultTableSelForeground;
    Color defaultTableSelBackground;
    int renamecout   = 0; 
    int successcount = 0; 
    int errorcount   = 0; 
    int warningcount = 0;
    Map<Integer, Integer> renameresult;

    public frmRenamer() {
	try {

	    icon = ImageIO.read(getClass().getResource("/resources/icons/appIcon-Red.png"));
	    this.setIconImage(icon);
	} catch (IOException /*| IllegalArgumentException*/ ex) {

	}
	this.setLocationRelativeTo(null);

	initComponents();

	customFont                = new Font("Red Hat Display", Font.PLAIN, 13);     
	boldFont                  = new Font("Red Hat Display Bold", Font.PLAIN, 13);
	metrics                   = new Canvas().getFontMetrics(customFont);         
	defaultTableForeground    = UIManager.getColor("Table.foreground");          
	defaultTableBackground    = UIManager.getColor("Table.background");          
	defaultTableSelBackground = UIManager.getColor("Table.selectionBackground"); 
	defaultTableSelForeground = UIManager.getColor("Table.selectionForeground");
	
	dtm = (DefaultTableModel) tblBookList.getModel();
	tblBookList.getTableHeader().setFont(boldFont);
	tblBookList.setRowHeight(customFont.getSize() + 10);

//	table cell padding
	tblBookList.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {
				   int topPadding = 4;
				   int leftPadding = 8;
				   int bottomPadding = 4;
				   int rightPadding = 8;

				   @Override
				   public Component getTableCellRendererComponent(JTable table, Object value,
										  boolean isSelected, boolean hasFocus,
										  int row, int column) {
				       super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				       setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));
				       return this;
				   }

			       });

	dtm.addTableModelListener(e -> {
	    if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.DELETE) {
		lblStatus.setText(tblBookList.getRowCount() + " Files for Renaming - Ready - Double Click on Table to Edit and Set name before Renaming");
		if (tblBookList.getRowCount() == 0) {
		    cmdRemoveAll.setEnabled(false);
		    cmdRemoveSelected.setEnabled(false);
		} else {
		    cmdRemoveAll.setEnabled(true);
		    cmdRemoveSelected.setEnabled(true);
		}
	    }
	});

	DefaultTableCellRenderer centerrenderer = new DefaultTableCellRenderer();
	centerrenderer.setHorizontalAlignment(SwingConstants.CENTER);
	tblBookList.getColumnModel().getColumn(0).setCellRenderer(centerrenderer);
	tblBookList.setOpaque(true);
	tblBookList.setBackground(Color.white);
	tblBookList.setFillsViewportHeight(true);

	chkAutoNext.setOpaque(false);
	chkCreateFolders.setOpaque(false);
	chkMoveToFolder.setOpaque(false);
	lblTitle.setFont(boldFont);
	txtPreview.setFont(boldFont);
	cmdSetName.setFont(boldFont);
	lblCompany.setFont(boldFont);

//	adding listeners
	DocumentListener docListener = new DocumentListener() {
	    @Override
	    public void insertUpdate(DocumentEvent e) {
		buildPreviewName();
	    }

	    @Override
	    public void removeUpdate(DocumentEvent e) {
		buildPreviewName();
	    }

	    @Override
	    public void changedUpdate(DocumentEvent e) {
		buildPreviewName();
	    } // Often unused for plain text
	};

	cboTopic.setEditable(true);
	topicEditor = (JTextComponent) cboTopic.getEditor().getEditorComponent();
	topicEditor.getDocument().addDocumentListener(docListener);
	//cboTopic.addItemListener(e -> buildPreviewName());

	txtTitle.getDocument().addDocumentListener(docListener);
	txtAuthor.getDocument().addDocumentListener(docListener);
	txtYear.getDocument().addDocumentListener(docListener);
	cboEdition.addItemListener(e -> buildPreviewName());
	cboLanguage.addItemListener(e -> buildPreviewName());
	
	renameresult = new HashMap();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneHeader = new javax.swing.JPanel();
        cmdOpenBook = new javax.swing.JButton();
        cmdChooseFolder = new javax.swing.JButton();
        cmdRemoveSelected = new javax.swing.JButton();
        cmdRemoveAll = new javax.swing.JButton();
        cmdChooseFiles = new javax.swing.JButton();
        paneEntry = new javax.swing.JPanel();
        scrollBookList = new javax.swing.JScrollPane();
        tblBookList = new javax.swing.JTable();
        lblTopic = new javax.swing.JLabel();
        cboTopic = new javax.swing.JComboBox<>();
        lblTitle = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        lblAuthor = new javax.swing.JLabel();
        txtAuthor = new javax.swing.JTextField();
        lblYear = new javax.swing.JLabel();
        txtYear = new javax.swing.JTextField();
        lblEdition = new javax.swing.JLabel();
        cboEdition = new javax.swing.JComboBox<>();
        lblLanguage = new javax.swing.JLabel();
        cboLanguage = new javax.swing.JComboBox<>();
        lblPreview = new javax.swing.JLabel();
        txtPreview = new javax.swing.JTextField();
        lblLength = new javax.swing.JLabel();
        lblNote = new javax.swing.JLabel();
        chkAutoNext = new javax.swing.JCheckBox();
        cmdSetName = new javax.swing.JButton();
        paneActions = new javax.swing.JPanel();
        cmdRenameSelected = new javax.swing.JButton();
        cmdRenameAll = new javax.swing.JButton();
        paneStatus = new javax.swing.JPanel();
        lblCompany = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        paneFolderChoice = new javax.swing.JPanel();
        chkCreateFolders = new javax.swing.JCheckBox();
        chkMoveToFolder = new javax.swing.JCheckBox();
        txtMoveToFolder = new javax.swing.JTextField();
        cmdBrowse = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JSoft Ebook Renamer");
        setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        setMinimumSize(new java.awt.Dimension(1000, 620));
        setName("frmRenamer"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1000, 620));

        paneHeader.setBackground(new java.awt.Color(242, 247, 247));

        cmdOpenBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/34-16.png"))); // NOI18N
        cmdOpenBook.setText("Open File In System");
        cmdOpenBook.setIconTextGap(8);
        cmdOpenBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOpenBookActionPerformed(evt);
            }
        });

        cmdChooseFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/52-16.png"))); // NOI18N
        cmdChooseFolder.setText("Choose Folder");
        cmdChooseFolder.setIconTextGap(8);
        cmdChooseFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdChooseFolderActionPerformed(evt);
            }
        });

        cmdRemoveSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/14-16.png"))); // NOI18N
        cmdRemoveSelected.setText("Remove Selected");
        cmdRemoveSelected.setEnabled(false);
        cmdRemoveSelected.setIconTextGap(8);
        cmdRemoveSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRemoveSelectedActionPerformed(evt);
            }
        });

        cmdRemoveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/33-16.png"))); // NOI18N
        cmdRemoveAll.setText("Remove All");
        cmdRemoveAll.setEnabled(false);
        cmdRemoveAll.setIconTextGap(8);
        cmdRemoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRemoveAllActionPerformed(evt);
            }
        });

        cmdChooseFiles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/2-16.png"))); // NOI18N
        cmdChooseFiles.setText("Choose Files");
        cmdChooseFiles.setIconTextGap(8);
        cmdChooseFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdChooseFilesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneHeaderLayout = new javax.swing.GroupLayout(paneHeader);
        paneHeader.setLayout(paneHeaderLayout);
        paneHeaderLayout.setHorizontalGroup(
            paneHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneHeaderLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(paneHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(paneHeaderLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmdOpenBook)
                        .addGap(18, 18, 18)
                        .addComponent(cmdRemoveSelected)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdRemoveAll))
                    .addGroup(paneHeaderLayout.createSequentialGroup()
                        .addComponent(cmdChooseFiles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdChooseFolder)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
        );
        paneHeaderLayout.setVerticalGroup(
            paneHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneHeaderLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(paneHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cmdChooseFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdChooseFiles, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdOpenBook, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdRemoveSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdRemoveAll, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        getContentPane().add(paneHeader, java.awt.BorderLayout.PAGE_START);

        paneEntry.setBackground(new java.awt.Color(248, 251, 251));

        scrollBookList.setBackground(new java.awt.Color(255, 255, 255));

        tblBookList.setBackground(new java.awt.Color(244, 244, 255));
        tblBookList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sl. No", "File Name", "Extension", "New Name Preview", "Topic", "Title", "Author", "Year", "Edition", "Language", "Folder"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBookList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblBookList.setGridColor(new java.awt.Color(230, 230, 230));
        tblBookList.setSelectionBackground(new java.awt.Color(147, 219, 255));
        tblBookList.setSelectionForeground(new java.awt.Color(1, 1, 1));
        tblBookList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tblBookList.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblBookList.setShowGrid(true);
        tblBookList.getTableHeader().setReorderingAllowed(false);
        tblBookList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBookListMouseClicked(evt);
            }
        });
        scrollBookList.setViewportView(tblBookList);
        if (tblBookList.getColumnModel().getColumnCount() > 0) {
            tblBookList.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblBookList.getColumnModel().getColumn(0).setMaxWidth(80);
            tblBookList.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblBookList.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblBookList.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblBookList.getColumnModel().getColumn(4).setPreferredWidth(120);
            tblBookList.getColumnModel().getColumn(5).setPreferredWidth(200);
            tblBookList.getColumnModel().getColumn(6).setPreferredWidth(160);
            tblBookList.getColumnModel().getColumn(7).setPreferredWidth(80);
            tblBookList.getColumnModel().getColumn(8).setPreferredWidth(80);
            tblBookList.getColumnModel().getColumn(9).setPreferredWidth(80);
            tblBookList.getColumnModel().getColumn(10).setPreferredWidth(120);
        }

        lblTopic.setText("Topic");

        cboTopic.setEditable(true);
        cboTopic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "C", "CPP", "C#", "Visual Basic", "Visual C", "Visual C++", "Visual C#", "Visual FoxPro", "MFC", "Java", "JavaScript", "Python", "Stories", "Novels", "Accountancy", "HTML", "CSS" }));
        cboTopic.setSelectedIndex(-1);
        cboTopic.setMinimumSize(new java.awt.Dimension(64, 24));
        cboTopic.setPreferredSize(new java.awt.Dimension(64, 24));
        cboTopic.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cboTopicFocusGained(evt);
            }
        });
        cboTopic.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                cboTopicInputMethodTextChanged(evt);
            }
        });

        lblTitle.setText("Title*");

        txtTitle.setMinimumSize(new java.awt.Dimension(64, 24));
        txtTitle.setPreferredSize(new java.awt.Dimension(64, 24));
        txtTitle.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTitleFocusGained(evt);
            }
        });

        lblAuthor.setText("Author");

        txtAuthor.setMinimumSize(new java.awt.Dimension(64, 24));
        txtAuthor.setPreferredSize(new java.awt.Dimension(64, 24));
        txtAuthor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAuthorFocusGained(evt);
            }
        });

        lblYear.setText("Year");

        txtYear.setMinimumSize(new java.awt.Dimension(64, 24));
        txtYear.setPreferredSize(new java.awt.Dimension(64, 24));
        txtYear.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtYearFocusGained(evt);
            }
        });
        txtYear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtYearKeyTyped(evt);
            }
        });

        lblEdition.setText("Edition");

        cboEdition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" }));
        cboEdition.setMinimumSize(new java.awt.Dimension(64, 24));
        cboEdition.setPreferredSize(new java.awt.Dimension(64, 24));

        lblLanguage.setText("Language");

        cboLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English_EN", "Malayalam_ML", "Tamil_TA", "Hindi_HI", "Russian_RU", "French_FR" }));
        cboLanguage.setMinimumSize(new java.awt.Dimension(64, 24));
        cboLanguage.setPreferredSize(new java.awt.Dimension(64, 24));

        lblPreview.setText("New Filename Preview (Original Extension will be Added When Renaming)");

        txtPreview.setEditable(false);
        txtPreview.setBackground(new java.awt.Color(241, 241, 186));
        txtPreview.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 6, 1, 6)));
        txtPreview.setDisabledTextColor(new java.awt.Color(51, 51, 0));
        txtPreview.setEnabled(false);

        lblLength.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLength.setText("Filename Length: ");

        lblNote.setText("Maximum 250 Characters for Filename ( Additional 6 characters reserved for original Extension )");

        chkAutoNext.setText("Auto Edit Next");

        cmdSetName.setText("Set Name");
        cmdSetName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSetNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneEntryLayout = new javax.swing.GroupLayout(paneEntry);
        paneEntry.setLayout(paneEntryLayout);
        paneEntryLayout.setHorizontalGroup(
            paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneEntryLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollBookList)
                    .addGroup(paneEntryLayout.createSequentialGroup()
                        .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTopic)
                            .addComponent(cboTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle)
                            .addComponent(txtTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAuthor)
                            .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblYear)
                            .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboEdition, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEdition))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLanguage)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneEntryLayout.createSequentialGroup()
                        .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(paneEntryLayout.createSequentialGroup()
                                .addComponent(lblNote)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chkAutoNext))
                            .addGroup(paneEntryLayout.createSequentialGroup()
                                .addComponent(lblPreview)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblLength))
                            .addComponent(txtPreview))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdSetName)))
                .addGap(16, 16, 16))
        );
        paneEntryLayout.setVerticalGroup(
            paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneEntryLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(scrollBookList, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addGap(16, 16, 16)
                .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLanguage)
                    .addComponent(lblTitle)
                    .addComponent(lblTopic)
                    .addComponent(lblAuthor)
                    .addComponent(lblYear)
                    .addComponent(lblEdition))
                .addGap(4, 4, 4)
                .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboEdition, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPreview)
                    .addComponent(lblLength))
                .addGap(4, 4, 4)
                .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdSetName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneEntryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNote)
                    .addComponent(chkAutoNext))
                .addGap(16, 16, 16))
        );

        getContentPane().add(paneEntry, java.awt.BorderLayout.CENTER);

        paneActions.setBackground(new java.awt.Color(224, 236, 236));

        cmdRenameSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/69-16.png"))); // NOI18N
        cmdRenameSelected.setText("Rename Selected");
        cmdRenameSelected.setIconTextGap(8);
        cmdRenameSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRenameSelectedActionPerformed(evt);
            }
        });

        cmdRenameAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/38-16.png"))); // NOI18N
        cmdRenameAll.setText("Rename All");
        cmdRenameAll.setIconTextGap(8);
        cmdRenameAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRenameAllActionPerformed(evt);
            }
        });

        paneStatus.setBackground(new java.awt.Color(245, 245, 245));

        lblCompany.setForeground(new java.awt.Color(0, 51, 153));
        lblCompany.setText("© 2025  J Soft - EBook Renamer Version 1.0.0");

        lblStatus.setBackground(new java.awt.Color(240, 240, 240));
        lblStatus.setText("Ready - Double Click on Table to Edit and Set name before Renaming");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout paneStatusLayout = new javax.swing.GroupLayout(paneStatus);
        paneStatus.setLayout(paneStatusLayout);
        paneStatusLayout.setHorizontalGroup(
            paneStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneStatusLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCompany)
                .addGap(16, 16, 16))
        );
        paneStatusLayout.setVerticalGroup(
            paneStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneStatusLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(paneStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paneStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblCompany)))
                .addGap(6, 6, 6))
        );

        paneFolderChoice.setBackground(new java.awt.Color(236, 243, 243));

        chkCreateFolders.setText("Create Topic Folder(s)");
        chkCreateFolders.setActionCommand("Create Topic Folder and Move Files to It.");
        chkCreateFolders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCreateFoldersActionPerformed(evt);
            }
        });

        chkMoveToFolder.setText("Move To Folder");
        chkMoveToFolder.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkMoveToFolderItemStateChanged(evt);
            }
        });

        txtMoveToFolder.setEditable(false);
        txtMoveToFolder.setForeground(new java.awt.Color(36, 36, 36));
        txtMoveToFolder.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtMoveToFolder.setEnabled(false);
        txtMoveToFolder.setMaximumSize(new java.awt.Dimension(480, 2147483647));

        cmdBrowse.setText("Browse");
        cmdBrowse.setEnabled(false);
        cmdBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneFolderChoiceLayout = new javax.swing.GroupLayout(paneFolderChoice);
        paneFolderChoice.setLayout(paneFolderChoiceLayout);
        paneFolderChoiceLayout.setHorizontalGroup(
            paneFolderChoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneFolderChoiceLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(paneFolderChoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneFolderChoiceLayout.createSequentialGroup()
                        .addComponent(chkMoveToFolder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMoveToFolder, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdBrowse))
                    .addComponent(chkCreateFolders))
                .addGap(16, 16, 16))
        );
        paneFolderChoiceLayout.setVerticalGroup(
            paneFolderChoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneFolderChoiceLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(paneFolderChoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkMoveToFolder)
                    .addComponent(txtMoveToFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkCreateFolders)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout paneActionsLayout = new javax.swing.GroupLayout(paneActions);
        paneActions.setLayout(paneActionsLayout);
        paneActionsLayout.setHorizontalGroup(
            paneActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paneStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(paneActionsLayout.createSequentialGroup()
                .addComponent(paneFolderChoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(120, 120, 120)
                .addComponent(cmdRenameSelected)
                .addGap(18, 18, 18)
                .addComponent(cmdRenameAll)
                .addGap(16, 16, 16))
        );
        paneActionsLayout.setVerticalGroup(
            paneActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneActionsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(paneActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cmdRenameAll, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdRenameSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paneFolderChoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(paneStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        getContentPane().add(paneActions, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkCreateFoldersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCreateFoldersActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_chkCreateFoldersActionPerformed

    private void cmdChooseFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdChooseFilesActionPerformed

	JFileChooser fileChooser = new JFileChooser("D:\\");
	fileChooser.setMultiSelectionEnabled(true);
	fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	fileChooser.setAcceptAllFileFilterUsed(false);
	fileChooser.setFileFilter(new FileNameExtensionFilter(
		"eBooks (*.epub, *.pdf, *.mobi, *.azw, *.azw3, *.fb2, *.djvu, *.lit, *.rtf, *.txt, *.chm, *.cbz, *.cbr)",
		"epub", "pdf", "mobi", "azw", "azw3", "fb2", "djvu", "lit", "rtf", "txt", "chm", "cbz", "cbr"
	));

	int result = fileChooser.showOpenDialog(this);
	if (result == JFileChooser.APPROVE_OPTION) {

	    File[] selectedFiles = fileChooser.getSelectedFiles();

	    if (selectedFiles != null && selectedFiles.length > 0) {
		Arrays.stream(selectedFiles).forEach(file -> {

		    int rows = dtm.getRowCount();
		    Path path = file.toPath();
		    String fileName = path.getFileName().toString();
		    String extension = "";
		    String nameOnly = fileName;
		    int dotIndex = fileName.lastIndexOf('.');

		    if (dotIndex > 0) {
			nameOnly = fileName.substring(0, dotIndex);
			extension = fileName.substring(dotIndex + 1);
		    }

		    String parent = path.getParent() != null ? path.getParent().toString() : "";

		    dtm.addRow(new Object[]{
			++rows, nameOnly, extension, "", "", "", "", "", "", "", parent
		    });
		    int colW = tblBookList.getColumnModel().getColumn(1).getWidth();
		    int nameW = metrics.stringWidth(nameOnly);
		    if (nameW > colW) {
			colW = nameW >= 600 ? 600 : nameW;
			tblBookList.getColumnModel().getColumn(1).setPreferredWidth(colW);
		    }

		});

	    }

	}


    }//GEN-LAST:event_cmdChooseFilesActionPerformed

    private void cmdOpenBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOpenBookActionPerformed
	int selRow = tblBookList.getSelectedRow();
	Path filePath = Paths.get(tblBookList.getValueAt(selRow, 10).toString() + "\\"
		+ tblBookList.getValueAt(selRow, 1).toString() + "."
		+ tblBookList.getValueAt(selRow, 2).toString());
	if (Desktop.isDesktopSupported()) {
	    try {
		Desktop.getDesktop().open(filePath.toFile());
	    } catch (IOException e) {
		System.out.println("Exception from opening book : -- \n" + e.getMessage());
	    }
	}
    }//GEN-LAST:event_cmdOpenBookActionPerformed

    private void cmdChooseFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdChooseFolderActionPerformed

	JFileChooser fileChooser = new JFileChooser();
	fileChooser.setMultiSelectionEnabled(false);
	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	fileChooser.setAcceptAllFileFilterUsed(false);

	int result = fileChooser.showOpenDialog(this);
	if (result == JFileChooser.APPROVE_OPTION) {
	    Path selectedPath = fileChooser.getSelectedFile().toPath();
	    String parent = selectedPath.toAbsolutePath().toString();
	    final List<String> EBOOK_EXTENSIONS = Arrays.asList(
		    "epub", "mobi", "azw", "azw3", "pdf", "fb2", "djvu", "lit", "rtf", "txt", "chm", "cbz", "cbr"
	    );

	    try (Stream<Path> files = Files.list(selectedPath)) {
		files.filter(Files::isRegularFile) // only files, no directories
			.forEach(path -> {
			    String fullName = new String(path.getFileName().toString().getBytes(), StandardCharsets.UTF_8);
			    int dotPos = fullName.lastIndexOf('.');
			    String nameOnly = (dotPos > 0) ? fullName.substring(0, dotPos) : fullName;
			    String extension = (dotPos > 0) ? fullName.substring(dotPos + 1) : "";

			    boolean isValidExt;
			    for (String ext : EBOOK_EXTENSIONS) {
				isValidExt = extension.trim().equalsIgnoreCase(ext);
				if (isValidExt) {
				    dtm.addRow(new Object[]{dtm.getRowCount() + 1, nameOnly, extension, "", "", "", "", "", "", "", parent});

				    int colW = tblBookList.getColumnModel().getColumn(1).getWidth();
				    int nameW = metrics.stringWidth(nameOnly);
				    if (nameW > colW) {
					colW = nameW >= 600 ? 600 : nameW;
					tblBookList.getColumnModel().getColumn(1).setPreferredWidth(colW);
				    }
				    break;
				}
			    }

			});
	    } catch (IOException e) {
		System.out.println("Exception from choosing folder : -- " + e.getMessage());
	    }
	}

    }//GEN-LAST:event_cmdChooseFolderActionPerformed

    private void txtYearKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtYearKeyTyped
	if (!Character.isDigit(evt.getKeyChar()) || txtYear.getText().length() >= 4) {
	    evt.consume();
	    return;
	}
    }//GEN-LAST:event_txtYearKeyTyped

    private void tblBookListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBookListMouseClicked

	if (evt.getClickCount() == 2) {
	    editRow = tblBookList.getSelectedRow();
	    readTableRow();
	}
    }//GEN-LAST:event_tblBookListMouseClicked

    private void txtTitleFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTitleFocusGained
	txtTitle.selectAll();
    }//GEN-LAST:event_txtTitleFocusGained

    private void txtAuthorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAuthorFocusGained
	txtAuthor.selectAll();
    }//GEN-LAST:event_txtAuthorFocusGained

    private void txtYearFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtYearFocusGained
	txtYear.selectAll();
    }//GEN-LAST:event_txtYearFocusGained

    private void cboTopicFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboTopicFocusGained

	cboTopic.getEditor().selectAll();
    }//GEN-LAST:event_cboTopicFocusGained

    private void cmdRemoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRemoveAllActionPerformed

	int result = JOptionPane.showConfirmDialog(this, "Are you sure to Remove all items list?", "Clear Confirmation", JOptionPane.YES_NO_OPTION);
	if (result == JOptionPane.YES_OPTION) {
	    dtm.setRowCount(0);
	    // set edit row is nothing
	    editRow = -1;
	    
	    clearFields();
	}
    }//GEN-LAST:event_cmdRemoveAllActionPerformed

    private void cmdRemoveSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRemoveSelectedActionPerformed
	
	int result = JOptionPane.showConfirmDialog(this, "Are you sure to Remove selected items list?", "Removal Confirmation", JOptionPane.YES_NO_OPTION);
	if (result == JOptionPane.YES_OPTION) {	    
	    int[] selectedRows = tblBookList.getSelectedRows();

	    // Convert view indices to model indices
	    List<Integer> modelIndices = Arrays.stream(selectedRows)
		    .map(tblBookList::convertRowIndexToModel)
		    .boxed()
		    .sorted(Collections.reverseOrder()) // remove from bottom up
		    .collect(Collectors.toList());

	    // Remove rows
	    for (int rowIndex : modelIndices) {
		dtm.removeRow(rowIndex);
	    }
	    // set edit row is nothing
	    editRow = -1;
	    
	    // renumber row
	    for (int i = 0; i < tblBookList.getRowCount(); i++) {
		tblBookList.setValueAt(i + 1, i, 0);
	    }
	    clearFields();
	}

    }//GEN-LAST:event_cmdRemoveSelectedActionPerformed

    private void cboTopicInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cboTopicInputMethodTextChanged

	buildPreviewName();
    }//GEN-LAST:event_cboTopicInputMethodTextChanged

    private void cmdSetNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSetNameActionPerformed

	writeTableRow();
    }//GEN-LAST:event_cmdSetNameActionPerformed

    private void cmdRenameAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRenameAllActionPerformed
	int[] rowIndex = new int[tblBookList.getRowCount()];
	for (int i =0;i<tblBookList.getRowCount();i++)
	    rowIndex[i]= i;
	 renameAt(rowIndex);
    }//GEN-LAST:event_cmdRenameAllActionPerformed

    private void cmdRenameSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRenameSelectedActionPerformed

	renameAt(tblBookList.getSelectedRows());
    }//GEN-LAST:event_cmdRenameSelectedActionPerformed

    private void chkMoveToFolderItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkMoveToFolderItemStateChanged
	txtMoveToFolder.setEnabled(chkMoveToFolder.isSelected());
	cmdBrowse.setEnabled(chkMoveToFolder.isSelected());
    }//GEN-LAST:event_chkMoveToFolderItemStateChanged

    private void cmdBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBrowseActionPerformed
	JFileChooser fileChooser = new JFileChooser();
	fileChooser.setMultiSelectionEnabled(false);
	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	fileChooser.setAcceptAllFileFilterUsed(false);

	int result = fileChooser.showOpenDialog(this);
	if (result == JFileChooser.APPROVE_OPTION) {
	    txtMoveToFolder.setText(fileChooser.getSelectedFile().getAbsolutePath());
	}
	
    }//GEN-LAST:event_cmdBrowseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboEdition;
    private javax.swing.JComboBox<String> cboLanguage;
    private javax.swing.JComboBox<String> cboTopic;
    private javax.swing.JCheckBox chkAutoNext;
    private javax.swing.JCheckBox chkCreateFolders;
    private javax.swing.JCheckBox chkMoveToFolder;
    private javax.swing.JButton cmdBrowse;
    private javax.swing.JButton cmdChooseFiles;
    private javax.swing.JButton cmdChooseFolder;
    private javax.swing.JButton cmdOpenBook;
    private javax.swing.JButton cmdRemoveAll;
    private javax.swing.JButton cmdRemoveSelected;
    private javax.swing.JButton cmdRenameAll;
    private javax.swing.JButton cmdRenameSelected;
    private javax.swing.JButton cmdSetName;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAuthor;
    private javax.swing.JLabel lblCompany;
    private javax.swing.JLabel lblEdition;
    private javax.swing.JLabel lblLanguage;
    private javax.swing.JLabel lblLength;
    private javax.swing.JLabel lblNote;
    private javax.swing.JLabel lblPreview;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTopic;
    private javax.swing.JLabel lblYear;
    private javax.swing.JPanel paneActions;
    private javax.swing.JPanel paneEntry;
    private javax.swing.JPanel paneFolderChoice;
    private javax.swing.JPanel paneHeader;
    private javax.swing.JPanel paneStatus;
    private javax.swing.JScrollPane scrollBookList;
    private javax.swing.JTable tblBookList;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JTextField txtMoveToFolder;
    private javax.swing.JTextField txtPreview;
    private javax.swing.JTextField txtTitle;
    private javax.swing.JTextField txtYear;
    // End of variables declaration//GEN-END:variables

    void buildPreviewName() {
	if(editRow<0)
	    return;

	previewname = new StringBuilder();

	if (!topicEditor.getText().isBlank()) {
//		previewname.append(sanitize(cboTopic.getSelectedItem().toString())).append("_");
	    previewname.append(sanitize(topicEditor.getText())).append("_");
	}

	if (!txtTitle.getText().isBlank()) {
	    previewname.append(sanitize(txtTitle.getText())).append("_");
	}
	
	if (!txtAuthor.getText().isBlank()) {
	    previewname.append("By_").append(sanitize(txtAuthor.getText())).append("_");
	}
	if (!txtYear.getText().isBlank()) {
	    previewname.append(txtYear.getText()).append("_");
	}
	previewname.append("Ed_").append(cboEdition.getSelectedItem().toString()).append("_");
	previewname.append(cboLanguage.getSelectedItem().toString().substring(cboLanguage.getSelectedItem().toString().lastIndexOf('_') + 1));

	if (previewname.length() >= 250) {
	    lblLength.setForeground(Color.red);
	    previewname.setLength(250);
	} else {
	    lblLength.setForeground(Color.black);
	}
	lblLength.setText("Filename Length : " + previewname.length() + " / 250  ( " + (250 - previewname.length()) + " left )");

    	txtPreview.setText(previewname.toString() + "." + tblBookList.getValueAt(editRow, 2).toString());

    }

    public static String sanitize(String input) {
	// Step 1: Replace reserved characters with "-"
	String cleaned = input.replaceAll("[\\\\/:*?\"<>|., `=]", "-");

	// Step 2: Replace multiple "-" with single "-"
	cleaned = cleaned.replaceAll("-{2,}", "-");

	// Step 3: Trim leading/trailing "-"
	cleaned = cleaned.replaceAll("^-+", "").replaceAll("-+$", "");

	// Step 4: Title case each word, without altering existing uppercase
	String[] words = cleaned.split("-");
	StringBuilder result = new StringBuilder();

	for (String word : words) {
	    if (!word.isEmpty()) {
		char first = word.charAt(0);
		String rest = word.length() > 1 ? word.substring(1) : "";
		result.append(Character.toUpperCase(first)).append(rest).append("-");
	    }
	}

	// Remove trailing "-"
	if (result.length() > 0 && result.charAt(result.length() - 1) == '-') {
	    result.setLength(result.length() - 1);
	}

	return result.toString();
    }

    private void readTableRow() {
	if (editRow >= 0) {
	    cboTopic.setSelectedItem(tblBookList.getValueAt(editRow, 4).toString());
	    txtTitle.setText(tblBookList.getValueAt(editRow, 5).toString().isBlank() ? tblBookList.getValueAt(editRow, 1).toString() : tblBookList.getValueAt(editRow, 5).toString());
	    txtAuthor.setText(tblBookList.getValueAt(editRow, 6).toString());
	    txtYear.setText(tblBookList.getValueAt(editRow, 7).toString());
	 
	    cboEdition.setSelectedIndex(0);
	    if (!tblBookList.getValueAt(editRow, 8).toString().isBlank()) {
		cboEdition.setSelectedItem(tblBookList.getValueAt(editRow, 8).toString());
	    }

	    String langCode = tblBookList.getValueAt(editRow, 9).toString();
	    cboLanguage.setSelectedIndex(0);
	    for (int i = 0; i < cboLanguage.getItemCount(); i++) {
		if (cboLanguage.getItemAt(i).endsWith(langCode)) {
		    cboLanguage.setSelectedIndex(i);
		    break;
		}
	    }
	}
    }

    private void writeTableRow() {
	if (editRow < 0) {
	    return;
	}

	tblBookList.setValueAt(txtPreview.getText(), editRow, 3);
	tblBookList.setValueAt(topicEditor.getText(), editRow, 4);
	tblBookList.setValueAt(txtTitle.getText(), editRow, 5);
	tblBookList.setValueAt(txtAuthor.getText(), editRow, 6);
	tblBookList.setValueAt(txtYear.getText(), editRow, 7);
	tblBookList.setValueAt(cboEdition.getSelectedItem().toString(), editRow, 8);
	tblBookList.setValueAt(cboLanguage.getSelectedItem().toString().substring(cboLanguage.getSelectedItem().toString().lastIndexOf('_') + 1), editRow, 9);

	if (chkAutoNext.isSelected()) {
	    if ((tblBookList.getRowCount() - 1) >= (editRow + 1)) {
		++editRow;
		tblBookList.getSelectionModel().setSelectionInterval(editRow, editRow);
		readTableRow();
	    }
	} else {
	    clearFields();
	}
    }

    private void clearFields() {
	cboTopic.setSelectedIndex(-1);
	txtTitle.setText("");
	txtAuthor.setText("");
	txtYear.setText("");
	cboEdition.setSelectedIndex(0);
	cboLanguage.setSelectedIndex(0);
	txtPreview.setText("");
	editRow = -1;
	renameresult.clear();
    }

    /*
    @parameter int result  : 0 for error, 1 for success, 2 for no file found
    */
    
    
    
    void renameAt(int[] rowIndex) {
	
	renameresult = new HashMap();	
	clearFields();
	tblBookList.getSelectionModel().clearSelection();
	
	renamecout   = rowIndex.length;
	successcount = 0;                                
	errorcount   = 0;                                
	warningcount = 0;
	
	for (int row : rowIndex) {

	    if (!tblBookList.getValueAt(row, 3).toString().isBlank()) // if there's a new name
	    {		
		Path orgFolderPath = Paths.get(tblBookList.getValueAt(row, 10).toString());              // from col 10 original parent folder path
		Path orgFilePath   = orgFolderPath.resolve(tblBookList.getValueAt(row, 1).toString()     // from col 1 original filename
							+ "." 
							+ tblBookList.getValueAt(row, 2).toString());    // from col 2 original extension
//		check if move to folder is enabled
		Path tarFolderPath = (chkMoveToFolder.isSelected() && !txtMoveToFolder.getText().isBlank())
					? Paths.get(txtMoveToFolder.getText())
					:orgFolderPath;			
		
		String topicName   = tblBookList.getValueAt(row, 4).toString();                          // column 4 - new generated name
		
//		check if new topic folder creation enabled
		if (chkCreateFolders.isSelected() && !topicName.isBlank()) {
//		    check if topic directory already exist
		    Path topicPath = tarFolderPath.resolve(topicName);
		    try {
			if (Files.notExists(topicPath)) 
//			 if topic folder dont exist
			{
//			    create a topic folder
			    Files.createDirectory(topicPath);
			}
			tarFolderPath = topicPath;
		    } catch (IOException ex) {
//			    if can create folder break loop and move for next
			++errorcount;
			renameresult.put(row, 4);
		    }
		}
		
		Path tarFilePath   = tarFolderPath.resolve(tblBookList.getValueAt(row, 3).toString());	// from col 3 target filename		    

		try {
		    if (Files.exists(orgFilePath)) {
			Files.move(orgFilePath, tarFilePath);
			++successcount;
			renameresult.put(row, 1); // put success
//			System.out.println("Renamed: " + actualName + "." + extension + " → " + newName);

		    } else {
			++warningcount;
			renameresult.put(row, 2); // put not found flag
//			System.out.println("Original file not found: " + originalPath.getFileName());
		    }
		} catch (IOException e) {
		    ++errorcount;
		    renameresult.put(row, 0); // put error flag
//		    System.out.println("Error renaming file: " + e.getMessage());
		}
	    } else // if there is no new name
	    {
		tblBookList.setValueAt("-- No Name to Rename --", row, 3);
	    }

	}
	
	
	StringBuilder sb = new StringBuilder("<html>");
	sb.append(tblBookList.getRowCount()).append(" Files for Renaming ");
	sb.append(" | <font color=\"blue\"><b>Processed : </b></font>").append(renamecout);
	sb.append(" | <font color=\"Green\"><b>Success : </b></font>").append(successcount);
	sb.append(" | <font color=\"Red\"><b>Error : </b></font>").append(errorcount);
	sb.append(" | <font color=\"orange\"><b>Not Found : </b></font>").append(warningcount);
	sb.append("</html>");

	lblStatus.setText(sb.toString());
	
//	System.out.println("rename result : " + renameresult.toString());
	
	setCellColor();
	JOptionPane.showMessageDialog(this, "Finished Renaming Files.");
	
    }
    
//    void setCellColor(int result, int targetRow, int targetCol) {
    void setCellColor() {
	DefaultTableCellRenderer cellRenderer;
	cellRenderer = new DefaultTableCellRenderer() {
	    int topPadding    = 4;
	    int leftPadding   = 8;
	    int bottomPadding = 4;
	    int rightPadding  = 8;

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
									 boolean isSelected, boolean hasFocus, int row, int column) {
		
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		setBorder(new EmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));
//		System.out.println("from rendere  - row index : " + row);
		 
		int result =(renameresult.get(row)!=null)?renameresult.get(row):-1;

		if (isSelected) {
		    setForeground(defaultTableSelForeground);
		    setBackground(defaultTableSelBackground);
		} 
		else {
		    setFont(getFont().deriveFont(Font.BOLD));
		    switch (result) {			    
			case 0: // error
			    setForeground(new Color(0xF3D2D5));  // red
			    setBackground(new Color(0xAF3514));
			    break;
			case 1: // success
			    setForeground(new Color(0x00881C)); // green
			    setBackground(new Color(0xCCFFCC));
			    break;
			case 2: // warning not found
			    setForeground(new Color(0xFF8732)); // orange
			    setBackground(new Color(0xFFE9DA));
			    break;
			case 3: // warning not found
			    setForeground(new Color(0xD1E8FF)); // blue
			    setBackground(new Color(0x005AB5));
			    break;
			case 4: // warning not found
			    setForeground(new Color(0xFFEFE0)); // other orange
			    setBackground(new Color(0xD86C00));
			    break;
			default:
			    setForeground(defaultTableForeground);
			    setBackground(defaultTableBackground);
			    setFont(getFont().deriveFont(Font.PLAIN));
			    break;
		    }			
		}

		return this;
	    }
	};
	tblBookList.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
	tblBookList.repaint();
    }
}
