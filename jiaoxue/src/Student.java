package jiaoxue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;


public class Student
{
    private Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
    String userMySql="root";
    String passwordMySql="150848";
    //Vector<Object> rowData,columnNames;
    Vector<Object> columnNames;
    static JTable table2=null;
    //定义数据库需要的全局变量
    PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
    public Student()
    {
        columnNames=new Vector<Object>();
        //设置列名
        columnNames.add("学号");
        columnNames.add("姓名");
        columnNames.add("班级");
        //rowData可以存放多行,开始从数据库里取

        try
        {
            String userMySql="root";
            String passwordMySql="150848";
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //得到连接
            ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
                    +userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");

            ps=ct.prepareStatement("select * from student");

            rs=ps.executeQuery();

            while(rs.next())
            {
                //rowData可以存放多行
                Vector<Object> hang=new Vector<Object>();
                hang.add(rs.getInt(1));
                hang.add(rs.getString(2));
                hang.add(rs.getString(3));


                //加入到rowData
                rowData.add(hang);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

            try
            {
                if(rs!=null)
                {
                    rs.close();
                }
                if(ps!=null)
                {
                    ps.close();
                }
                if(ct!=null)
                {
                    ct.close();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        table2 = new JTable(rowData,columnNames)
        {

            private static final long serialVersionUID = -4333506764666173598L;

            public boolean isCellEditable(int row, int column)
            {
                return false;   //表格不允许被编辑
            }
        };
        table2.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
        tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
        table2.setDefaultRenderer(Object.class, tcr);
    }
    public void refreshStudent()
    {
        // TODO 自动生成的方法存根
        new Student();
        SystemUI.scrollPane2.setViewportView(Student.table2);
    }

    public void searchStudent()
    {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame SeaStu = new JFrame("查找学生信息");
        SeaStu.setSize(250, 100);
        SeaStu.setLocation(600, 300);
        JPanel seastu = new JPanel();
        JLabel sno = new JLabel("请输入学号");
        JTextField snotext  = new JTextField();
        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        seastu.setLayout(null);

        sno.setBounds(5,5,70,20);
        snotext.setBounds(80,5,120,20);
        ok.setBounds(50,30,60,20);
        reset.setBounds(130,30,60,20);

        seastu.add(sno);
        seastu.add(snotext);
        seastu.add(ok);
        seastu.add(reset);

        SeaStu.add(seastu);
        SeaStu.setVisible(true);

        SeaStu.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                columnNames=new Vector<Object>();
                //设置列名
                columnNames.add("学号");
                columnNames.add("姓名");

                columnNames.add("班级");
                String str = snotext.getText();
                //rowData = new Vector<Object>();
                int count=0;
                //rowData可以存放多行,开始从数据库里取
                try
                {
                    //加载驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
                            +userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
                    if(!snotext.getText().trim().equals(""))
                    {

                        ps=ct.prepareStatement("select * from student where studentid='"+str+"'");

                        rs=ps.executeQuery();
                    }
                    if(snotext.getText().trim().equals(""))
                    {
                        ps=ct.prepareStatement("select * from student ");
                        JOptionPane.showMessageDialog(null, "请输入查询信息！");
                        rs=ps.executeQuery();
                    }
                    while(rs.next())
                    {
                        //rowData可以存放多行
                        Vector<Object> hang=new Vector<Object>();
                        hang.add(rs.getInt(1));
                        hang.add(rs.getString(2));
                        hang.add(rs.getString(3));

                        //加入到rowData
                        rowData.add(hang);
                        count++;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "查询失败！");
                }
                finally
                {
                    try
                    {
                        if(rs!=null)
                        {
                            rs.close();
                        }
                        if(ps!=null)
                        {
                            ps.close();
                        }
                        if(ct!=null)
                        {
                            ct.close();
                        }
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                if(count!=0)
                {
                    table2 = new JTable(rowData,columnNames)
                    {

                        private static final long serialVersionUID = -8131400305374145108L;

                        public boolean isCellEditable(int row, int column)
                        {
                            return false;   //表格不允许被编辑
                        }
                    };
                    table2.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
                    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
                    tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
                    table2.setDefaultRenderer(Object.class, tcr);
                    SystemUI.scrollPane2.setViewportView(table2);
                    SeaStu.dispose();
                    SystemUI.clickable();
                }
                if(count==0)
                {
                    JOptionPane.showMessageDialog(null, "查无此人！");
                }
            }
        });
        reset.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                snotext.setText("");
            }
        });
    }

    public void deleteStudent()
    {
        // TODO 自动生成的方法存根
        int row = table2.getSelectedRow();
        if ( row >= 0 )
        {
            String string[] = new String[3];
            string[0] = String.valueOf(table2.getValueAt(row, 0));
            string[1] = String.valueOf(table2.getValueAt(row, 1));
            try
            {
                //加载驱动
                Class.forName("com.mysql.jdbc.Driver");
                //得到连接
                ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
                        +userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
                ps=ct.prepareStatement("delete from student where studentid='"+string[0]+"' and studentname='"+string[1]+"'");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "删除成功！");
                refreshStudent();
                row=-1;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "删除失败！");
            }
            finally
            {
                try
                {
                    if(rs!=null)
                    {
                        rs.close();
                    }
                    if(ps!=null)
                    {
                        ps.close();
                    }
                    if(ct!=null)
                    {
                        ct.close();
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "请选中要删除的行！");
        }
    }

    public void addStudent()
    {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame AddStu = new JFrame("添加学生信息");
        AddStu.setSize(250, 270);
        AddStu.setLocation(600, 300);
        JPanel addStu = new JPanel();
        JLabel sno = new JLabel("学号");
        JLabel sname = new JLabel("姓名");

        JLabel sclass = new JLabel("班级");
        JTextField snotext  = new JTextField();
        JTextField snametext = new JTextField();

        JTextField sclasstext  = new JTextField();
        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        addStu.setLayout(null);

        sno.setBounds(5,5,70,20);
        snotext.setBounds(80,5,120,20);
        sname.setBounds(5,30,70,20);
        snametext.setBounds(80,30,120,20);

        sclass.setBounds(5,60,70,20);
        sclasstext.setBounds(80,60,120,20);
        ok.setBounds(50,190,60,20);
        reset.setBounds(130,190,60,20);

        addStu.add(sno);
        addStu.add(snotext);
        addStu.add(sname);
        addStu.add(snametext);

        addStu.add(sclass);
        addStu.add(sclasstext);
        addStu.add(ok);
        addStu.add(reset);

        AddStu.add(addStu);
        AddStu.setVisible(true);

        AddStu.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                try
                {
                    String userMySql="root";
                    String passwordMySql="150848";

                    String name = snametext.getText();

                    String sclass = sclasstext.getText();
                    int no = Integer.valueOf(snotext.getText()).intValue();

                    //加载驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
                            +userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
                    ps=ct.prepareStatement("insert into student values('"+no+"','"+name+"','"+sclass+"')");
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "添加成功！");
                    AddStu.dispose();
                    SystemUI.clickable();
                    refreshStudent();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "添加失败！");
                    SystemUI.unclickable();
                }
                finally
                {
                    try
                    {
                        if(rs!=null)
                        {
                            rs.close();
                        }
                        if(ps!=null)
                        {
                            ps.close();
                        }
                        if(ct!=null)
                        {
                            ct.close();
                        }
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        });
        reset.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {

                snotext.setText("");
                snametext.setText("");

                sclasstext.setText("");
            }
        });
    }
    //修改学生信息；
    public void updateStudent()
    {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame UpdateStu = new JFrame("修改学生信息");
        UpdateStu.setSize(250, 250);
        UpdateStu.setLocation(600, 300);
        JPanel updatestu = new JPanel();
        JLabel upno = new JLabel("要修改的学号");
        JLabel ssex = new JLabel("要改成的姓名");

        JLabel upclass = new JLabel("要改成的班级");
        JTextField upnotext = new JTextField();
        JTextField ssextext  = new JTextField();

        JTextField upclasstext  = new JTextField();
        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        updatestu.setLayout(null);

        upno.setBounds(5,5,100,20);
        upnotext.setBounds(110,5,120,20);
        ssex.setBounds(5,30,100,20);
        ssextext.setBounds(110,30,120,20);

        upclass.setBounds(5,60,100,20);
        upclasstext.setBounds(110,60,120,20);
        ok.setBounds(50,170,60,20);
        reset.setBounds(120,170,60,20);

        updatestu.add(upno);
        updatestu.add(upnotext);
        updatestu.add(ssex);
        updatestu.add(ssextext);

        updatestu.add(upclass);
        updatestu.add(upclasstext);
        updatestu.add(ok);
        updatestu.add(reset);

        UpdateStu.add(updatestu);
        UpdateStu.setVisible(true);

        UpdateStu.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                try
                {
                    String sclass = upclasstext.getText();
                    String sname=ssextext.getText();
                    int yno = Integer.valueOf(upnotext.getText()).intValue();

                    //加载驱动
                    Class.forName("com.mysql.jdbc.Drivera");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
                            +userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
                    ps=ct.prepareStatement("update student set  class ='"+sclass+"',studentname='"+sname+"' where studentid='"+yno+"'");
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "修改成功！");
                    UpdateStu.dispose();
                    SystemUI.clickable();
                    refreshStudent();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "修改失败！");
                    SystemUI.unclickable();
                }
                finally
                {
                    try
                    {
                        if(rs!=null)
                        {
                            rs.close();
                        }
                        if(ps!=null)
                        {
                            ps.close();
                        }
                        if(ct!=null)
                        {
                            ct.close();
                        }
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        });
        reset.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {

                upclasstext.setText("");
            }
        });
    }

}

