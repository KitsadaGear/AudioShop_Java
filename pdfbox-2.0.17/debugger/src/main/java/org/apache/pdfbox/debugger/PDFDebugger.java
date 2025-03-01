/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pdfbox.debugger;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.spi.IIORegistry;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.TreePath;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSBoolean;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSInteger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSNull;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.debugger.colorpane.CSArrayBased;
import org.apache.pdfbox.debugger.colorpane.CSDeviceN;
import org.apache.pdfbox.debugger.colorpane.CSIndexed;
import org.apache.pdfbox.debugger.colorpane.CSSeparation;
import org.apache.pdfbox.debugger.flagbitspane.FlagBitsPane;
import org.apache.pdfbox.debugger.fontencodingpane.FontEncodingPaneController;
import org.apache.pdfbox.debugger.pagepane.PagePane;
import org.apache.pdfbox.debugger.streampane.StreamPane;
import org.apache.pdfbox.debugger.stringpane.StringPane;
import org.apache.pdfbox.debugger.treestatus.TreeStatus;
import org.apache.pdfbox.debugger.treestatus.TreeStatusPane;
import org.apache.pdfbox.debugger.ui.ArrayEntry;
import org.apache.pdfbox.debugger.ui.DocumentEntry;
import org.apache.pdfbox.debugger.ui.ErrorDialog;
import org.apache.pdfbox.debugger.ui.ExtensionFileFilter;
import org.apache.pdfbox.debugger.ui.FileOpenSaveDialog;
import org.apache.pdfbox.debugger.ui.MapEntry;
import org.apache.pdfbox.debugger.ui.OSXAdapter;
import org.apache.pdfbox.debugger.ui.PDFTreeCellRenderer;
import org.apache.pdfbox.debugger.ui.PDFTreeModel;
import org.apache.pdfbox.debugger.ui.PageEntry;
import org.apache.pdfbox.debugger.ui.ReaderBottomPanel;
import org.apache.pdfbox.debugger.ui.RecentFiles;
import org.apache.pdfbox.debugger.ui.RotationMenu;
import org.apache.pdfbox.debugger.ui.Tree;
import org.apache.pdfbox.debugger.ui.WindowPrefs;
import org.apache.pdfbox.debugger.ui.ZoomMenu;
import org.apache.pdfbox.filter.FilterFactory;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDPageLabels;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceCMYK;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.apache.pdfbox.printing.PDFPageable;

/**
 * PDF Debugger.
 * 
 * @author wurtz
 * @author Ben Litchfield
 * @author Khyrul Bashar
 */
@SuppressWarnings({"serial","squid:MaximumInheritanceDepth","squid:S1948"})
public class PDFDebugger extends JFrame
{
    private static final Set<COSName> SPECIALCOLORSPACES =
            new HashSet<COSName>(Arrays.asList(COSName.INDEXED, COSName.SEPARATION, COSName.DEVICEN));

    private static final Set<COSName> OTHERCOLORSPACES =
            new HashSet<COSName>(Arrays.asList(COSName.ICCBASED, COSName.PATTERN, COSName.CALGRAY,
                                 COSName.CALRGB, COSName.LAB));

    @SuppressWarnings({"squid:S2068"})
    private static final String PASSWORD = "-password";
    private static final String VIEW_STRUCTURE = "-viewstructure";

    private static final int SHORCUT_KEY_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
    
    private TreeStatusPane statusPane;
    private RecentFiles recentFiles;
    private WindowPrefs windowPrefs;
    private boolean isPageMode;

    private PDDocument document;
    private String currentFilePath;
    
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final boolean IS_MAC_OS = OS_NAME.startsWith("mac os x");
    
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextPane jTextPane1;
    private ReaderBottomPanel statusBar;
    private Tree tree;
    private final JPanel documentPanel = new JPanel();
    
    // file menu
    private JMenuItem saveAsMenuItem;
    private JMenuItem saveMenuItem;
    private JMenu recentFilesMenu;
    private JMenuItem printMenuItem;
    private JMenuItem reopenMenuItem;
    
    // edit > find menu
    private JMenu findMenu;
    private JMenuItem findMenuItem;
    private JMenuItem findNextMenuItem;
    private JMenuItem findPreviousMenuItem;

    // view menu
    private JMenuItem viewModeItem;
    
    public static JCheckBoxMenuItem allowSubsampling;
    
    /**
     * Constructor.
     */
    public PDFDebugger()
    {
        this(false);
    }

    /**
     * Constructor.
     */
    public PDFDebugger(boolean viewPages)
    {
        isPageMode = viewPages;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents()
    {
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new JScrollPane();
        tree = new Tree(this);
        jScrollPane2 = new JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        
        tree.setCellRenderer(new PDFTreeCellRenderer());
        tree.setModel(null);

        setTitle("Apache PDFBox Debugger");

        addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowOpened(WindowEvent windowEvent)
            {
                tree.requestFocusInWindow();
                super.windowOpened(windowEvent);
            }

            @Override
            public void windowClosing(WindowEvent evt)
            {
                exitForm(evt);
            }
        });

        windowPrefs = new WindowPrefs(this.getClass());

        jScrollPane1.setBorder(new BevelBorder(BevelBorder.RAISED));
        jSplitPane1.setDividerLocation(windowPrefs.getDividerLocation());
        tree.addTreeSelectionListener(new TreeSelectionListener()
        {
            @Override
            public void valueChanged(TreeSelectionEvent evt)
            {
                jTree1ValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(tree);

        jSplitPane1.setRightComponent(jScrollPane2);
        jSplitPane1.setDividerSize(3);

        jScrollPane2.setViewportView(jTextPane1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        JScrollPane documentScroller = new JScrollPane();
        documentScroller.setViewportView(documentPanel);

        statusPane = new TreeStatusPane(tree);
        statusPane.getPanel().setBorder(new BevelBorder(BevelBorder.RAISED));
        statusPane.getPanel().setPreferredSize(new Dimension(300, 25));
        getContentPane().add(statusPane.getPanel(), BorderLayout.PAGE_START);

        getContentPane().add(jSplitPane1, BorderLayout.CENTER);

        statusBar = new ReaderBottomPanel();
        getContentPane().add(statusBar, BorderLayout.SOUTH);

        // create menus
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createViewMenu());
        setJMenuBar(menuBar);

        setExtendedState(windowPrefs.getExtendedState());
        setBounds(windowPrefs.getBounds());

        // drag and drop to open files
        setTransferHandler(new TransferHandler()
        {
            @Override
            public boolean canImport(TransferSupport transferSupport)
            {
                return transferSupport.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            @SuppressWarnings("unchecked")
            public boolean importData(TransferSupport transferSupport)
            {
                try
                {
                    Transferable transferable = transferSupport.getTransferable();
                    List<File> files = (List<File>) transferable.getTransferData(
                            DataFlavor.javaFileListFlavor);
                    readPDFFile(files.get(0), "");
                    return true;
                }
                catch (IOException e)
                {
                    new ErrorDialog(e).setVisible(true);
                    return true;
                }
                catch (UnsupportedFlavorException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });

        initGlobalEventHandlers();
    }

    /**
     * Initialize application global event handlers. Protected to allow
     * subclasses to override this method if they don't want the global event
     * handler overridden.
     */
    @SuppressWarnings("WeakerAccess")
    protected void initGlobalEventHandlers()
    {
        // Mac OS X file open/quit handler
        if (IS_MAC_OS)
        {
            try
            {
                Method osxOpenFiles = getClass().getDeclaredMethod("osxOpenFiles", String.class);
                osxOpenFiles.setAccessible(true);
                OSXAdapter.setFileHandler(this, osxOpenFiles);

                Method osxQuit = getClass().getDeclaredMethod("osxQuit");
                osxQuit.setAccessible(true);
                OSXAdapter.setQuitHandler(this, osxQuit);
            }
            catch (NoSuchMethodException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
    
    private JMenu createFileMenu()
    {
        JMenuItem openMenuItem = new JMenuItem("Open...");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORCUT_KEY_MASK));
        openMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                openMenuItemActionPerformed(evt);
            }
        });

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openMenuItem);
        fileMenu.setMnemonic('F');

        JMenuItem openUrlMenuItem = new JMenuItem("Open URL...");
        openUrlMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, SHORCUT_KEY_MASK));
        openUrlMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                String urlString = JOptionPane.showInputDialog("Enter an URL");
                if (urlString == null || urlString.isEmpty())
                {
                    return;
                }
                try
                {
                    readPDFurl(urlString, "");
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
        fileMenu.add(openUrlMenuItem);
        
        reopenMenuItem = new JMenuItem("Reopen");
        reopenMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, SHORCUT_KEY_MASK));
        reopenMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                try
                {
                    if (currentFilePath.startsWith("http"))
                    {
                        readPDFurl(currentFilePath, "");
                    }
                    else
                    {
                        readPDFFile(currentFilePath, "");
                    }
                }
                catch (IOException e)
                {
                    new ErrorDialog(e).setVisible(true);
                }
            }
        });
        reopenMenuItem.setEnabled(false);
        fileMenu.add(reopenMenuItem);

        try
        {
            recentFiles = new RecentFiles(this.getClass(), 5);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        recentFilesMenu = new JMenu("Open Recent");
        recentFilesMenu.setEnabled(false);
        addRecentFileItems();
        fileMenu.add(recentFilesMenu);

        printMenuItem = new JMenuItem("Print");
        printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, SHORCUT_KEY_MASK));
        printMenuItem.setEnabled(false);
        printMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                printMenuItemActionPerformed(evt);
            }
        });

        fileMenu.addSeparator();
        fileMenu.add(printMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
        exitMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                exitMenuItemActionPerformed(evt);
            }
        });

        if (!IS_MAC_OS)
        {
            fileMenu.addSeparator();
            fileMenu.add(exitMenuItem);
        }
        
        return fileMenu;
    }
    
    private JMenu createEditMenu()
    {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.setEnabled(false);
        editMenu.add(cutMenuItem);

        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.setEnabled(false);
        editMenu.add(copyMenuItem);

        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.setEnabled(false);
        editMenu.add(pasteMenuItem);

        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.setEnabled(false);
        editMenu.add(deleteMenuItem);
        editMenu.addSeparator();
        editMenu.add(createFindMenu());
        
        return editMenu;
    }
    
    private JMenu createViewMenu()
    {
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('V');
        if (isPageMode)
        {
            viewModeItem = new JMenuItem("Show Internal Structure");
        }
        else
        {
            viewModeItem = new JMenuItem("Show Pages");
        }
        viewModeItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                if (isPageMode)
                {
                    viewModeItem.setText("Show Pages");
                    isPageMode = false;
                }
                else
                {
                    viewModeItem.setText("Show Internal Structure");
                    isPageMode = true;
                }
                if (document != null)
                {
                    initTree();
                }
            }
        });
        viewMenu.add(viewModeItem);

        ZoomMenu zoomMenu = ZoomMenu.getInstance();
        zoomMenu.setEnableMenu(false);
        viewMenu.add(zoomMenu.getMenu());

        RotationMenu rotationMenu = RotationMenu.getInstance();
        rotationMenu.setEnableMenu(false);
        viewMenu.add(rotationMenu.getMenu());
        
        viewMenu.addSeparator();

        allowSubsampling = new JCheckBoxMenuItem("Allow subsampling");
        allowSubsampling.setEnabled(false);
        viewMenu.add(allowSubsampling);
        
        return viewMenu;
    }
    
    private JMenu createFindMenu()
    {
        findMenu = new JMenu("Find");
        findMenu.setEnabled(false);
        
        findMenuItem = new JMenuItem("Find...");
        findMenuItem.setActionCommand("find");
        findMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, SHORCUT_KEY_MASK));
        
        findNextMenuItem = new JMenuItem("Find Next");
        if (IS_MAC_OS)
        {
            findNextMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, SHORCUT_KEY_MASK));
        }
        else
        {
            findNextMenuItem.setAccelerator(KeyStroke.getKeyStroke("F3"));
        }

        findPreviousMenuItem = new JMenuItem("Find Previous");
        if (IS_MAC_OS)
        {
            findPreviousMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_G, SHORCUT_KEY_MASK | InputEvent.SHIFT_DOWN_MASK));
        }
        else
        {
            findPreviousMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_F3, InputEvent.SHIFT_DOWN_MASK));
        }
        
        findMenu.add(findMenuItem);
        findMenu.add(findNextMenuItem);
        findMenu.add(findPreviousMenuItem);
        
        return findMenu;
    }

    /**
     * Returns the File menu.
     */
    public JMenu getFindMenu()
    {
        return findMenu;
    }

    /**
     * Returns the Edit &gt; Find &gt; Find menu item.
     */
    public JMenuItem getFindMenuItem()
    {
        return findMenuItem;
    }

    /**
     * Returns the Edit &gt; Find &gt; Find Next menu item.
     */
    public JMenuItem getFindNextMenuItem()
    {
        return findNextMenuItem;
    }

    /**
     * Returns the Edit &gt; Find &gt; Find Previous menu item.
     */
    public JMenuItem getFindPreviousMenuItem()
    {
        return findPreviousMenuItem;
    }
    
    /**
     * This method is called via reflection on Mac OS X.
     */
    private void osxOpenFiles(String filename)
    {
        try
        {
            readPDFFile(filename, "");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called via reflection on Mac OS X.
     */
    private void osxQuit()
    {
        exitMenuItemActionPerformed(null);
    }

    private void openMenuItemActionPerformed(ActionEvent evt)
    {
        try
        {
            if (IS_MAC_OS)
            {
                FileDialog openDialog = new FileDialog(this, "Open");
                openDialog.setFilenameFilter(new FilenameFilter()
                {
                    @Override
                    public boolean accept(File dir, String name)
                    {
                        return name.toLowerCase().endsWith(".pdf");
                    }
                });
                openDialog.setVisible(true);
                if (openDialog.getFile() != null)
                {
                    readPDFFile(new File(openDialog.getDirectory(),openDialog.getFile()), "");
                }
            }
            else
            {
                String[] extensions = new String[] {"pdf", "PDF"};
                FileFilter pdfFilter = new ExtensionFileFilter(extensions, "PDF Files (*.pdf)");
                FileOpenSaveDialog openDialog = new FileOpenSaveDialog(this, pdfFilter);

                File file = openDialog.openFile();
                if (file != null)
                {
                    readPDFFile(file, "");
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void jTree1ValueChanged(TreeSelectionEvent evt)
    {
        TreePath path = tree.getSelectionPath();
        if (path != null)
        {
            try
            {
                Object selectedNode = path.getLastPathComponent();
                
                statusBar.getStatusLabel().setText("");
                
                if (isPage(selectedNode))
                {
                    showPage(selectedNode);
                    return;
                }
                
                if (isSpecialColorSpace(selectedNode) || isOtherColorSpace(selectedNode))
                {
                    showColorPane(selectedNode);
                    return;
                }
                if (path.getParentPath() != null
                        && isFlagNode(selectedNode, path.getParentPath().getLastPathComponent()))
                {
                    Object parentNode = path.getParentPath().getLastPathComponent();
                    showFlagPane(parentNode, selectedNode);
                    return;
                }
                if (isStream(selectedNode))
                {
                    showStream((COSStream) getUnderneathObject(selectedNode), path);
                    return;
                }
                if (isFont(selectedNode))
                {
                    showFont(selectedNode, path);
                    return;
                }
                if (isString(selectedNode))
                {
                    showString(selectedNode);
                    return;
                }
                if (jSplitPane1.getRightComponent() == null
                        || !jSplitPane1.getRightComponent().equals(jScrollPane2))
                {
                    replaceRightComponent(jScrollPane2);
                }
                jTextPane1.setText(convertToString(selectedNode));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isSpecialColorSpace(Object selectedNode)
    {
        selectedNode = getUnderneathObject(selectedNode);

        if (selectedNode instanceof COSArray && ((COSArray) selectedNode).size() > 0)
        {
            COSBase arrayEntry = ((COSArray)selectedNode).get(0);
            if (arrayEntry instanceof COSName)
            {
                COSName name = (COSName) arrayEntry;
                return SPECIALCOLORSPACES.contains(name);
            }
        }
        return false;
    }

    private boolean isOtherColorSpace(Object selectedNode)
    {
        selectedNode = getUnderneathObject(selectedNode);

        if (selectedNode instanceof COSArray && ((COSArray) selectedNode).size() > 0)
        {
            COSBase arrayEntry = ((COSArray)selectedNode).get(0);
            if (arrayEntry instanceof COSName)
            {
                COSName name = (COSName) arrayEntry;
                return OTHERCOLORSPACES.contains(name);
            }
        }
        return false;
    }

    private boolean isPage(Object selectedNode)
    {
        selectedNode = getUnderneathObject(selectedNode);

        if (selectedNode instanceof COSDictionary)
        {
            COSDictionary dict = (COSDictionary) selectedNode;
            COSBase typeItem = dict.getItem(COSName.TYPE);
            if (COSName.PAGE.equals(typeItem))
            {
                return true;
            }
        }
        else if (selectedNode instanceof PageEntry)
        {
            return true;
        }
        return false;
    }

    private boolean isFlagNode(Object selectedNode, Object parentNode)
    {
        if (selectedNode instanceof MapEntry)
        {
            Object key = ((MapEntry) selectedNode).getKey();
            return (COSName.FLAGS.equals(key) && isFontDescriptor(parentNode)) || 
                    (COSName.F.equals(key) && isAnnot(parentNode)) || 
                    COSName.FF.equals(key) || 
                    COSName.PANOSE.equals(key) ||
                    COSName.SIG_FLAGS.equals(key) ||
                    (COSName.P.equals(key) && isEncrypt(parentNode));
        }
        return false;
    }

    private boolean isEncrypt(Object obj)
    {
        if (obj instanceof MapEntry)
        {
            MapEntry entry = (MapEntry) obj;
            return COSName.ENCRYPT.equals(entry.getKey()) && entry.getValue() instanceof COSDictionary;
        }
        return false;
    }

    private boolean isFontDescriptor(Object obj)
    {
        Object underneathObject = getUnderneathObject(obj);
        return underneathObject instanceof COSDictionary &&
                ((COSDictionary) underneathObject).containsKey(COSName.TYPE) &&
                ((COSDictionary) underneathObject).getCOSName(COSName.TYPE).equals(COSName.FONT_DESC);
    }

    private boolean isAnnot(Object obj)
    {
        Object underneathObject = getUnderneathObject(obj);
        return underneathObject instanceof COSDictionary &&
                ((COSDictionary) underneathObject).containsKey(COSName.TYPE) &&
                ((COSDictionary) underneathObject).getCOSName(COSName.TYPE).equals(COSName.ANNOT);
    }

    private boolean isStream(Object selectedNode)
    {
        return getUnderneathObject(selectedNode) instanceof COSStream;
    }

    private boolean isString(Object selectedNode)
    {
        return getUnderneathObject(selectedNode) instanceof COSString;
    }

    private boolean isFont(Object selectedNode)
    {
        selectedNode = getUnderneathObject(selectedNode);
        if (selectedNode instanceof COSDictionary)
        {
            COSDictionary dic = (COSDictionary)selectedNode;
            return dic.containsKey(COSName.TYPE) &&
                    dic.getCOSName(COSName.TYPE).equals(COSName.FONT) &&
                    !isCIDFont(dic);
        }
        return false;
    }

    private boolean isCIDFont(COSDictionary dic)
    {
        return dic.containsKey(COSName.SUBTYPE) &&
                (dic.getCOSName(COSName.SUBTYPE).equals(COSName.CID_FONT_TYPE0)
                || dic.getCOSName(COSName.SUBTYPE).equals(COSName.CID_FONT_TYPE2));
    }

    /**
     * Show a Panel describing color spaces in more detail and interactive way.
     * @param csNode the special color space containing node.
     */
    private void showColorPane(Object csNode)
    {
        csNode = getUnderneathObject(csNode);

        if (csNode instanceof COSArray && ((COSArray) csNode).size() > 0)
        {
            COSArray array = (COSArray)csNode;
            COSBase arrayEntry = array.get(0);
            if (arrayEntry instanceof COSName)
            {
                COSName csName = (COSName) arrayEntry;
                if (csName.equals(COSName.SEPARATION))
                {
                    replaceRightComponent(new CSSeparation(array).getPanel());
                }
                else if (csName.equals(COSName.DEVICEN))
                {
                    replaceRightComponent(new CSDeviceN(array).getPanel());
                }
                else if (csName.equals(COSName.INDEXED))
                {
                    replaceRightComponent(new CSIndexed(array).getPanel());
                }
                else if (OTHERCOLORSPACES.contains(csName))
                {
                    replaceRightComponent(new CSArrayBased(array).getPanel());
                }
            }
        }
    }

    private void showPage(Object selectedNode)
    {
        selectedNode = getUnderneathObject(selectedNode);

        COSDictionary page;
        if (selectedNode instanceof COSDictionary)
        {
            page = (COSDictionary) selectedNode;
        }
        else
        {
            page = ((PageEntry) selectedNode).getDict();
        }

        COSBase typeItem = page.getItem(COSName.TYPE);
        if (COSName.PAGE.equals(typeItem))
        {
            PagePane pagePane = new PagePane(document, page, statusBar.getStatusLabel());
            replaceRightComponent(new JScrollPane(pagePane.getPanel()));
        }
    }

    private void showFlagPane(Object parentNode, Object selectedNode)
    {
        parentNode = getUnderneathObject(parentNode);
        if (parentNode instanceof COSDictionary)
        {
            selectedNode = ((MapEntry)selectedNode).getKey();
            selectedNode = getUnderneathObject(selectedNode);
            FlagBitsPane flagBitsPane = new FlagBitsPane(document,
                    (COSDictionary) parentNode,
                    (COSName) selectedNode);
            replaceRightComponent(flagBitsPane.getPane());
        }
    }

    private void showStream(COSStream stream, TreePath path) throws IOException
    {
        boolean isContentStream = false;
        boolean isThumb = false;

        COSName key = getNodeKey(path.getLastPathComponent());
        COSName parentKey = getNodeKey(path.getParentPath().getLastPathComponent());
        COSDictionary resourcesDic = null;

        if (COSName.CONTENTS.equals(key))
        {
            Object pageObj = path.getParentPath().getLastPathComponent();
            COSDictionary page = (COSDictionary) getUnderneathObject(pageObj);
            resourcesDic = (COSDictionary) page.getDictionaryObject(COSName.RESOURCES);
            isContentStream = true;
        }
        else if (COSName.CONTENTS.equals(parentKey) || COSName.CHAR_PROCS.equals(parentKey))
        {
            Object pageObj = path.getParentPath().getParentPath().getLastPathComponent();
            COSDictionary page = (COSDictionary) getUnderneathObject(pageObj);
            resourcesDic = (COSDictionary) page.getDictionaryObject(COSName.RESOURCES);
            isContentStream = true;
        }
        else if (COSName.FORM.equals(stream.getCOSName(COSName.SUBTYPE)) ||
                COSName.PATTERN.equals(stream.getCOSName(COSName.TYPE)) ||
                stream.getInt(COSName.PATTERN_TYPE) == 1)
        {
            if (stream.containsKey(COSName.RESOURCES))
            {
                resourcesDic = (COSDictionary) stream.getDictionaryObject(COSName.RESOURCES);
            }
            isContentStream = true;
        }
        else if (COSName.THUMB.equals(key))
        {
            resourcesDic = null;
            isThumb = true;
        }
        else if (COSName.IMAGE.equals((stream).getCOSName(COSName.SUBTYPE)))
        {
            // not to be used for /Thumb, even if it contains /Subtype /Image
            Object resourcesObj = path.getParentPath().getParentPath().getLastPathComponent();
            resourcesDic = (COSDictionary) getUnderneathObject(resourcesObj);
        }
        StreamPane streamPane = new StreamPane(stream, isContentStream, isThumb, resourcesDic);
        replaceRightComponent(streamPane.getPanel());
    }

    private void showFont(Object selectedNode, TreePath path)
    {
        COSName fontName = getNodeKey(selectedNode);
        COSDictionary resourceDic = (COSDictionary) getUnderneathObject(path.getParentPath().getParentPath().getLastPathComponent());

        FontEncodingPaneController fontEncodingPaneController = new FontEncodingPaneController(fontName, resourceDic);
        JPanel pane = fontEncodingPaneController.getPane();
        if (pane == null)
        {
            // unsupported font type
            replaceRightComponent(jScrollPane2);
            return;
        }
        replaceRightComponent(pane);
    }

    // replace the right component while keeping divider position
    private void replaceRightComponent(Component pane)
    {
        int div = jSplitPane1.getDividerLocation();
        jSplitPane1.setRightComponent(pane);
        jSplitPane1.setDividerLocation(div);
    }

    private void showString(Object selectedNode)
    {
        COSString string = (COSString)getUnderneathObject(selectedNode);
        replaceRightComponent(new StringPane(string).getPane());
    }

    private COSName getNodeKey(Object selectedNode)
    {
        if (selectedNode instanceof MapEntry)
        {
            return ((MapEntry) selectedNode).getKey();
        }
        return null;
    }

    private Object getUnderneathObject(Object selectedNode)
    {
        if (selectedNode instanceof MapEntry)
        {
            selectedNode = ((MapEntry) selectedNode).getValue();
        }
        else if (selectedNode instanceof ArrayEntry)
        {
            selectedNode = ((ArrayEntry) selectedNode).getValue();
        }
        else if (selectedNode instanceof PageEntry)
        {
            selectedNode = ((PageEntry) selectedNode).getDict();
        }

        if (selectedNode instanceof COSObject)
        {
            selectedNode = ((COSObject) selectedNode).getObject();
        }
        return selectedNode;
    }

    private String convertToString( Object selectedNode )
    {
        String data = null;
        if(selectedNode instanceof COSBoolean)
        {
            data = "" + ((COSBoolean)selectedNode).getValue();
        }
        else if( selectedNode instanceof COSFloat )
        {
            data = "" + ((COSFloat)selectedNode).floatValue();
        }
        else if( selectedNode instanceof COSNull )
        {
            data = "null";
        }
        else if( selectedNode instanceof COSInteger )
        {
            data = "" + ((COSInteger)selectedNode).intValue();
        }
        else if( selectedNode instanceof COSName )
        {
            data = "" + ((COSName)selectedNode).getName();
        }
        else if( selectedNode instanceof COSString )
        {
            String text = ((COSString) selectedNode).getString();
            // display unprintable strings as hex
            for (char c : text.toCharArray())
            {
                if (Character.isISOControl(c))
                {
                    text = "<" + ((COSString) selectedNode).toHexString() + ">";
                    break;
                }
            }
            data = "" + text;
        }
        else if( selectedNode instanceof COSStream )
        {
            try
            {
                COSStream stream = (COSStream) selectedNode;
                InputStream in = stream.createInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                IOUtils.copy(in, baos);
                data = baos.toString();
            }
            catch( IOException e )
            {
                throw new RuntimeException(e);
            }
        }
        else if( selectedNode instanceof MapEntry )
        {
            data = convertToString( ((MapEntry)selectedNode).getValue() );
        }
        else if( selectedNode instanceof ArrayEntry )
        {
            data = convertToString( ((ArrayEntry)selectedNode).getValue() );
        }
        return data;
    }

    private void exitMenuItemActionPerformed(ActionEvent ignored)
    {
        if( document != null )
        {
            try
            {
                document.close();
                if (!currentFilePath.startsWith("http"))
                {
                    recentFiles.addFile(currentFilePath);
                }
                recentFiles.close();
            }
            catch( IOException e )
            {
                throw new RuntimeException(e);
            }
        }
        windowPrefs.setExtendedState(getExtendedState());
        this.setExtendedState(Frame.NORMAL);
        windowPrefs.setBounds(getBounds());
        windowPrefs.setDividerLocation(jSplitPane1.getDividerLocation());
        performApplicationExit();
    }

    /**
     * Exit the application after the window is closed. This is protected to let
     * subclasses override the behavior.
     */
    @SuppressWarnings("WeakerAccess")
    protected void performApplicationExit()
    {
        System.exit(0);
    }

    private void printMenuItemActionPerformed(ActionEvent evt)
    {
        if (document == null)
        {
            return;
        }
        AccessPermission ap = document.getCurrentAccessPermission();
        if (!ap.canPrint())
        {
            JOptionPane.showMessageDialog(this, "You do not have permission to print");
            return;
        }

        try
        {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            PDViewerPreferences vp = document.getDocumentCatalog().getViewerPreferences();
            if (vp != null && vp.getDuplex() != null)
            {
                String dp = vp.getDuplex();
                if (PDViewerPreferences.DUPLEX.DuplexFlipLongEdge.toString().equals(dp))
                {
                    pras.add(Sides.TWO_SIDED_LONG_EDGE);
                }
                else if (PDViewerPreferences.DUPLEX.DuplexFlipShortEdge.toString().equals(dp))
                {
                    pras.add(Sides.TWO_SIDED_SHORT_EDGE);
                }
                else if (PDViewerPreferences.DUPLEX.Simplex.toString().equals(dp))
                {
                    pras.add(Sides.ONE_SIDED);
                }
            }
            if (job.printDialog(pras))
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try
                {
                    job.print(pras);
                }
                finally
                {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        }
        catch (PrinterException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Exit the Application.
     */
    private void exitForm(WindowEvent evt)
    {
        exitMenuItemActionPerformed(null);
    }

    /**
     * Entry point.
     * 
     * @param args the command line arguments
     * @throws Exception If anything goes wrong.
     */
    public static void main(String[] args) throws Exception
    {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        if (System.getProperty("apple.laf.useScreenMenuBar") == null)
        {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        
        // handle uncaught exceptions
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable)
            {
                new ErrorDialog(throwable).setVisible(true);
            }
        });

        // trigger premature initializations for more accurate rendering benchmarks
        // See discussion in PDFBOX-3988
        if (PDType1Font.COURIER.isStandard14())
        {
            // Yes this is always true
            PDDeviceCMYK.INSTANCE.toRGB(new float[] { 0, 0, 0, 0} );
            PDDeviceRGB.INSTANCE.toRGB(new float[] { 0, 0, 0 } );
            IIORegistry.getDefaultInstance();
            FilterFactory.INSTANCE.getFilter(COSName.FLATE_DECODE);
        }

        // open file, if any
        String filename = null;
        @SuppressWarnings({"squid:S2068"})
        String password = "";
        boolean viewPages = true;
        
        for( int i = 0; i < args.length; i++ )
        {
            if( args[i].equals( PASSWORD ) )
            {
                i++;
                if( i >= args.length )
                {
                    usage();
                }
                password = args[i];
            }
            else if( args[i].equals(VIEW_STRUCTURE) )
            {
                viewPages = false;
            }
            else
            {
                filename = args[i];
            }
        }
        final PDFDebugger viewer = new PDFDebugger(viewPages);
        
        
        if (filename != null)
        {
            File file = new File(filename);
            if (file.exists())
            {
                viewer.readPDFFile( filename, password );
            }
        }
        viewer.setVisible(true);
    }

    private void readPDFFile(String filePath, String password) throws IOException
    {
        File file = new File(filePath);
        readPDFFile(file, password);
    }
    
    private void readPDFFile(final File file, String password) throws IOException
    {
        if( document != null )
        {
            document.close();
            if (!currentFilePath.startsWith("http"))
            {
                recentFiles.addFile(currentFilePath);
            }
        }
        currentFilePath = file.getPath();
        recentFiles.removeFile(file.getPath());
        DocumentOpener documentOpener = new DocumentOpener(password)
        {
            @Override
            PDDocument open() throws IOException
            {
                return PDDocument.load(file, password);
            }
        };
        document = documentOpener.parse();
        printMenuItem.setEnabled(true);
        reopenMenuItem.setEnabled(true);
        
        initTree();
        
        if (IS_MAC_OS)
        {
            setTitle(file.getName());
            getRootPane().putClientProperty("Window.documentFile", file);
        }
        else
        {
            setTitle("PDF Debugger - " + file.getAbsolutePath());
        }
        addRecentFileItems();
    }
    
    private void readPDFurl(final String urlString, String password) throws IOException
    {
        if (document != null)
        {
            document.close();
            if (!currentFilePath.startsWith("http"))
            {
                recentFiles.addFile(currentFilePath);
            }
        }
        currentFilePath = urlString;
        DocumentOpener documentOpener = new DocumentOpener(password)
        {
            @Override
            PDDocument open() throws IOException
            {
                return PDDocument.load(new URL(urlString).openStream(), password);
            }
        };
        document = documentOpener.parse();
        printMenuItem.setEnabled(true);
        reopenMenuItem.setEnabled(true);

        initTree();

        if (IS_MAC_OS)
        {
            setTitle(urlString);
        }
        else
        {
            setTitle("PDF Debugger - " + urlString);
        }
        addRecentFileItems();
    }
    
    private void initTree()
    {
        TreeStatus treeStatus = new TreeStatus(document.getDocument().getTrailer());
        statusPane.updateTreeStatus(treeStatus);
        
        if (isPageMode)
        {
            File file = new File(currentFilePath);
            DocumentEntry documentEntry = new DocumentEntry(document, file.getName());
            ZoomMenu.getInstance().resetZoom();
            RotationMenu.getInstance().setRotationSelection(RotationMenu.ROTATE_0_DEGREES);
            tree.setModel(new PDFTreeModel(documentEntry));
            // Root/Pages/Kids/[0] is not always the first page, so use the first row instead:
            tree.setSelectionPath(tree.getPathForRow(1));
        }
        else
        {
            tree.setModel(new PDFTreeModel(document));
            tree.setSelectionPath(treeStatus.getPathForString("Root"));
        }
    }

    /**
     * Internal class to avoid double code in password entry loop.
     */
    abstract class DocumentOpener
    {
        String password;

        DocumentOpener(String password)
        {
            this.password = password;
        }

        /**
         * Override to load the actual input type (File, URL, stream), don't call it directly!
         * 
         * @return
         * @throws IOException 
         */
        abstract PDDocument open() throws IOException;

        /**
         * Call this!
         * 
         * @return
         * @throws IOException 
         */
        final PDDocument parse() throws IOException 
        {
            while (true)
            {
                try
                {
                    return open();
                }
                catch (InvalidPasswordException ipe)
                {
                    // https://stackoverflow.com/questions/8881213/joptionpane-to-get-password
                    JPanel panel = new JPanel();
                    JLabel label = new JLabel("Password:");
                    JPasswordField pass = new JPasswordField(10);
                    panel.add(label);
                    panel.add(pass);
                    String[] options = new String[]
                    {
                        "OK", "Cancel"
                    };
                    int option = JOptionPane.showOptionDialog(null, panel, "Enter password",
                            JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, "");
                    if (option == 0)
                    {
                        password = new String(pass.getPassword());
                        continue;
                    }
                    throw ipe;
                }
            }
        }
    }

    private void addRecentFileItems()
    {
        Action recentMenuAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String filePath = (String) ((JComponent) actionEvent.getSource()).getClientProperty("path");
                try
                {
                    readPDFFile(filePath, "");
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e);
                }
            }
        };
        if (!recentFiles.isEmpty())
        {
            recentFilesMenu.removeAll();
            List<String> files = recentFiles.getFiles();
            for (int i = files.size() - 1; i >= 0; i--)
            {
                String path = files.get(i);
                String name = new File(path).getName();
                JMenuItem recentFileMenuItem = new JMenuItem(name);
                recentFileMenuItem.putClientProperty("path", path);
                recentFileMenuItem.addActionListener(recentMenuAction);
                recentFilesMenu.add(recentFileMenuItem);
            }
            recentFilesMenu.setEnabled(true);
        }
    }

    /**
     * This will print out a message telling how to use this utility.
     */
    private static void usage()
    {
        String message = "Usage: java -jar pdfbox-app-x.y.z.jar PDFDebugger [options] <inputfile>\n"
                + "\nOptions:\n"
                + "  -password <password> : Password to decrypt the document\n"
                + "  -viewstructure       : activate structure mode on startup\n"
                + "  <inputfile>          : The PDF document to be loaded\n";
        
        System.err.println(message);
        System.exit(1);
    }

    /**
     * Convenience method to get the page label if available.
     * 
     * @param document
     * @param pageIndex 0-based page number.
     * @return a page label or null if not available.
     */
    public static String getPageLabel(PDDocument document, int pageIndex)
    {
        PDPageLabels pageLabels;
        try
        {
            pageLabels = document.getDocumentCatalog().getPageLabels();
        }
        catch (IOException ex)
        {
            return ex.getMessage();
        }
        if (pageLabels != null)
        {
            String[] labels = pageLabels.getLabelsByPageIndices();
            if (labels[pageIndex] != null)
            {
                return labels[pageIndex];
            }
        }
        return null;
    }
}
