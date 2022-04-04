package ru.vsu.cs.buchnev;

import ru.vsu.cs.util.ArrayUtils;
import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.buchnev.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTable tableInput;
    private JButton buttonLoadInputFromFile;
    private JButton buttonRandomInput;
    private JButton buttonSaveInputInfoFile;
    private JButton buttonReverseRows;
    private JButton buttonReverseColumns;

    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;
    private JMenu menuLookAndFeel;
    private int count = 0;
    private int countR = 0;
    private boolean flag = true;
    private List<SortState> listState1;
    private int[] arr;


    public FrameMain() {
        this.setTitle("FrameMain");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableInput, 40, true, true, true, true);
        //tableOutput.setEnabled(false);
        tableInput.setRowHeight(25);

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        JTableUtils.writeArrayToJTable(tableInput, new int[]{0, 15, 2, 3, 4});
        this.pack();


        buttonLoadInputFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        arr = ArrayUtils.readIntArrayFromFile(fileChooserOpen.getSelectedFile().getPath());
                        JTableUtils.writeArrayToJTable(tableInput, arr);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
        buttonRandomInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    arr = ArrayUtils.createRandomIntArray(tableInput.getColumnCount(),-100,100);
                    JTableUtils.writeArrayToJTable(tableInput, arr);
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
        buttonSaveInputInfoFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(countR == -2){
                        countR = 0;
                    }
                    countR++;
                    if(countR >= arr.length-1){
                        SwingUtils.showInfoMessageBox("Ошибка!Выход за количество шагов сортировки!");
                        countR--;
                    }
                    else {
                        JTableUtils.writeArrayToJTable(tableInput, listState1.get(countR).getArr());
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
        buttonReverseRows.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Timer timer = new Timer(1000,null);
                    int[] data1 = JTableUtils.readIntArrayFromJTable(tableInput);
                    listState1 = Main.sort(data1);
                    int delay = 1000;
                    ActionListener listener = new AbstractAction() {
                        //int i = 0;
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(count<listState1.size()) {
                                JTableUtils.writeArrayToJTable(tableInput, listState1.get(count).getArr());
                                count++;
                            }
                            else{
                                System.out.println("Сортировка по таймеру окончена!");
                                SwingUtils.showInfoMessageBox("Сортировка по таймеру окончена!");
                                flag = false;
                                timer.stop();
                            }
                            countR = count-1;
                        }
                    };
                    timer.addActionListener(listener);
                    timer.start();
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
        buttonReverseColumns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    countR--;
                    if(countR == -2)
                        SwingUtils.showInfoMessageBox("Ошибка!Выход за количество шагов сортировки!");
                    else {

                        if (countR != -1) {
                            JTableUtils.writeArrayToJTable(tableInput, listState1.get(countR).getArr());
                        } else {
                            JTableUtils.writeArrayToJTable(tableInput, arr);
                        }
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
