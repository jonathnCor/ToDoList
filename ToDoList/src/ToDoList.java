import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToDoList {
    private JFrame frame;
    private JTextField taskField;
    private DefaultListModel<String> model;
    private JList<String> list;
    private ArrayList<String> tasks;

    public ToDoList() {
        tasks = new ArrayList<>();
        createWindow();
        createInputField();
        createTaskList();
        createStatusBar();
    }

    private void createWindow() {
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 500);
    }

    private void createInputField() {
        JPanel panel = new JPanel();
        taskField = new JTextField(20);
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText();
                if (!task.isEmpty()) {
                    tasks.add(task);
                    model.addElement(task);
                    taskField.setText("");
                    updateStatusBar();
                }
            }
        });

        panel.add(taskField);
        panel.add(addButton);
        frame.add(panel, BorderLayout.NORTH);
    }

    private void createTaskList() {
        model = new DefaultListModel<>();
        list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    tasks.remove(index);
                    model.remove(index);
                    updateStatusBar();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    private void createStatusBar() {
        JLabel statusLabel = new JLabel();
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(statusLabel, BorderLayout.SOUTH);

        updateStatusBar();
    }

    private void updateStatusBar() {
        int totalTasks = tasks.size();
        JLabel statusLabel = (JLabel) frame.getContentPane().getComponent(2);
        statusLabel.setText("Total Tasks: " + totalTasks);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ToDoList().frame.setVisible(true);
            }
        });
    }
}
