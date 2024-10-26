package jiaoxue;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class Score
{
    String userMySql="root";
    String passwordMySql="150848";
    private Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
    //Vector<Object> rowData;
    Vector<Object> columnNames;
    //定义数据库需要的全局变量
    PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
    static JTable table4=null;
    static JTable table6=null;
    static JTable table7=null;

    public Score()
    {
        columnNames=new Vector<Object>();
        //设置列名
        columnNames.add("学号");
        columnNames.add("姓名");
        columnNames.add("课程号");
        columnNames.add("课程名");
        columnNames.add("成绩");

        //rowData = new Vector<Object>();
        //rowData可以存放多行,开始从数据库里取

        try
        {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //得到连接
            ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
                    +userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
            ps=ct.prepareStatement("select * from score");

            rs=ps.executeQuery();

            while(rs.next())
            {
                //rowData可以存放多行
                Vector<Object> hang=new Vector<Object>();
                hang.add(rs.getString(1));
                hang.add(rs.getString(2));
                hang.add(rs.getString(3));
                hang.add(rs.getString(4));
                hang.add(rs.getInt(5));


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
        table4 = new JTable(rowData,columnNames)
        {

            private static final long serialVersionUID = 1525421110274312344L;

            public boolean isCellEditable(int row, int column)
            {
                return false;   //表格不允许被编辑
            }
        };
        table4.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
        tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
        table4.setDefaultRenderer(Object.class, tcr);
    }
    public void addScore()
    {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame Addsc = new JFrame("添加成绩信息");
        Addsc.setSize(250, 180);
        Addsc.setLocation(600, 300);
        JPanel addsc = new JPanel();
        JLabel scsno = new JLabel("学号");
        JLabel sccno = new JLabel("课程号");
        JLabel score = new JLabel("成绩");
        JTextField scsnotext  = new JTextField();
        JTextField sccnotext = new JTextField();
        JTextField scoretext  = new JTextField();

        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        addsc.setLayout(null);
        scsno.setBounds(5,5,70,20);
        scsnotext.setBounds(80,5,120,20);
        sccno.setBounds(5,30,70,20);
        sccnotext.setBounds(80,30,120,20);
        score.setBounds(5,60,70,20);
        scoretext.setBounds(80,60,120,20);
        ok.setBounds(50,100,60,20);
        reset.setBounds(130,100,60,20);

        addsc.add(scsno);
        addsc.add(scsnotext);
        addsc.add(sccno);
        addsc.add(sccnotext);
        addsc.add(score);
        addsc.add(scoretext);
        addsc.add(ok);
        addsc.add(reset);

        Addsc.add(addsc);
        Addsc.setVisible(true);

        Addsc.addWindowListener(new WindowAdapter()
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
                    String sno = scsnotext.getText();
                    String cno = sccnotext.getText();
                    int score = Integer.valueOf(scoretext.getText()).intValue();
                    int count=0;
                    //加载驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
                            +userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
                    ps=ct.prepareStatement("select studentname from selectcourse where studentid='"+sno+"' and courseid='"+cno+"'");
                    rs=ps.executeQuery();
                    while(rs.next())
                    {
                        count++;
                    }
                    if(count==0)
                    {
                        JOptionPane.showMessageDialog(null, "此人没有选此课程！");
                    }
                    if(count!=0)
                    {
                        ps=ct.prepareStatement("insert into score values('"+sno+"',(select studentname from selectcourse where studentid='"+sno+"' and courseid='"+cno+"'),'"+cno+"',(select coursename from selectcourse where courseid='"+cno+"' and studentid='"+sno+"'),"+score+")");
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "添加成功！");
                        Addsc.dispose();
                        SystemUI.clickable();
                        refreshScore();
                    }
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
                scsnotext.setText("");
                sccnotext.setText("");
                scoretext.setText("");
            }
        });
    }

    public void updateScore()
    {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame UpdateSC = new JFrame("修改成绩信息");
        UpdateSC.setSize(250, 240);
        UpdateSC.setLocation(600, 300);
        JPanel updatesc = new JPanel();
        JLabel yscsno = new JLabel("要修改的学号");
        JLabel scsno = new JLabel("要改成的学号");
        JLabel ysccno = new JLabel("要修改的课程号");
        JLabel sccno = new JLabel("要改成的课程号");
        JLabel score = new JLabel("成绩");
        JTextField yscsnotext = new JTextField();
        JTextField scsnotext  = new JTextField();
        JTextField ysccnotext = new JTextField();
        JTextField sccnotext = new JTextField();
        JTextField scoretext  = new JTextField();

        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        updatesc.setLayout(null);
        yscsno.setBounds(5,5,100,20);
        yscsnotext.setBounds(110,5,120,20);
        scsno.setBounds(5,30,100,20);
        scsnotext.setBounds(110,30,120,20);
        ysccno.setBounds(5,60,100,20);
        ysccnotext.setBounds(110,60,120,20);
        sccno.setBounds(5,90,100,20);
        sccnotext.setBounds(110,90,120,20);
        score.setBounds(5,120,100,20);
        scoretext.setBounds(110,120,120,20);
        ok.setBounds(50,160,60,20);
        reset.setBounds(120,160,60,20);

        updatesc.add(yscsno);
        updatesc.add(yscsnotext);
        updatesc.add(scsno);
        updatesc.add(scsnotext);
        updatesc.add(ysccno);
        updatesc.add(ysccnotext);
        updatesc.add(sccno);
        updatesc.add(sccnotext);
        updatesc.add(score);
        updatesc.add(scoretext);
        updatesc.add(ok);
        updatesc.add(reset);

        UpdateSC.add(updatesc);
        UpdateSC.setVisible(true);

        UpdateSC.addWindowListener(new WindowAdapter()
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
                    String ysno=yscsnotext.getText();
                    String ycno=ysccnotext.getText();
                    String sno = scsnotext.getText();
                    String cno = sccnotext.getText();
                    int score = Integer.valueOf(scoretext.getText()).intValue();
                    int count=0;
                    //加载驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb","root","root");
                    ps=ct.prepareStatement("select studentname from selectcourse where studentid='"+sno+"' and courseid='"+cno+"'");
                    rs=ps.executeQuery();
                    while(rs.next())
                    {
                        count++;
                    }
                    if(count==0)
                    {
                        JOptionPane.showMessageDialog(null, "此人没有选此课程！");
                    }
                    if(count!=0)
                    {
                        ps=ct.prepareStatement("update score set studentid ='"+sno+"', courseid ='"+cno+"',studentname=(select studentname from selectcourse where studentid='"+sno+"'and courseid ='"+cno+"'), coursename =(select coursename from selectcourse where courseid='"+cno+"'and studentid='"+sno+"'), score ="+score+" where studentid='"+ysno+"'and courseid='"+ycno+"'");
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "修改成功！");
                        UpdateSC.dispose();
                        SystemUI.clickable();
                        refreshScore();
                    }
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
                scsnotext.setText("");
                sccnotext.setText("");
                scoretext.setText("");
            }
        });
    }

    public void deleteScore()
    {
        // TODO 自动生成的方法存根
        int row = table4.getSelectedRow();
        if ( row >= 0 )
        {
            String string[] = new String[4];
            string[0] = (String) table4.getValueAt(row, 0);
            string[1] = (String) table4.getValueAt(row, 1);
            string[2] = (String) table4.getValueAt(row, 2);
            string[3] = (String) table4.getValueAt(row, 3);
            try
            {
                //加载驱动
                Class.forName("com.mysql.jdbc.Driver");
                //得到连接
                ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
                        +userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
                ps=ct.prepareStatement("delete from score where studentid='"+string[0]+"' and studentname='"+string[1]+"' and courseid='"+string[2]+"' and coursename='"+string[3]+"'");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "删除成功！");
                refreshScore();
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

    public void searchScore()
    {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame SeaSC = new JFrame("查找成绩信息");
        SeaSC.setSize(250, 150);
        SeaSC.setLocation(600, 300);
        JPanel seasc = new JPanel();
        JLabel cno = new JLabel("请输入课程号");
        JTextField cnotext  = new JTextField();
        JLabel sno = new JLabel("请输入学号");
        JTextField snotext  = new JTextField();
        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        seasc.setLayout(null);

        cno.setBounds(5,5,80,20);
        cnotext.setBounds(90,5,120,20);
        sno.setBounds(5,30,80,20);
        snotext.setBounds(90,30,120,20);
        ok.setBounds(50,70,60,20);
        reset.setBounds(130,70,60,20);

        seasc.add(cno);
        seasc.add(cnotext);
        seasc.add(sno);
        seasc.add(snotext);
        seasc.add(ok);
        seasc.add(reset);

        SeaSC.add(seasc);
        SeaSC.setVisible(true);

        SeaSC.addWindowListener(new WindowAdapter()
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
                columnNames.add("课程号");
                columnNames.add("课程名");
                columnNames.add("成绩");
                String str = cnotext.getText();
                String str1 = snotext.getText();
                //rowData = new Vector<Object>();
                int count=0;
                //rowData可以存放多行,开始从数据库里取
                try
                {
                    //加载驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?useUnicode=true&characterEncoding=utf8","root","150848");
                    if(snotext.getText().trim().equals("")&&!cnotext.getText().trim().equals(""))
                    {
                        ps=ct.prepareStatement("select * from score where courseid='"+str+"'");
                        rs=ps.executeQuery();
                    }
                    if(cnotext.getText().trim().equals("")&&!snotext.getText().trim().equals(""))
                    {
                        ps=ct.prepareStatement("select * from score where studentid='"+str1+"'");
                        rs=ps.executeQuery();
                    }
                    if(!cnotext.getText().trim().equals("")&&!snotext.getText().trim().equals(""))
                    {
                        ps=ct.prepareStatement("select * from score where studentid='"+str1+"' and courseid='"+str+"'");
                        rs=ps.executeQuery();
                    }
                    if(cnotext.getText().trim().equals("")&&snotext.getText().trim().equals(""))
                    {
                        ps=ct.prepareStatement("select * from score order by score desc ");
                        rs=ps.executeQuery();
                        JOptionPane.showMessageDialog(null, "请输入查询信息！");
                    }
                    while(rs.next())
                    {
                        //rowData可以存放多行
                        Vector<Object> hang=new Vector<Object>();
                        hang.add(rs.getString(1));
                        hang.add(rs.getString(2));
                        hang.add(rs.getString(3));
                        hang.add(rs.getString(4));
                        hang.add(rs.getInt(5));
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
                    table4 = new JTable(rowData,columnNames)
                    {
                        private static final long serialVersionUID = -518524586936803727L;
                        public boolean isCellEditable(int row, int column)
                        {
                            return false;   //表格不允许被编辑
                        }
                    };
                    table4.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
                    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
                    tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
                    table4.setDefaultRenderer(Object.class, tcr);
                    SystemUI.scrollPane4.setViewportView(table4);
                    SeaSC.dispose();
                    SystemUI.clickable();
                }
                if(count==0)
                {
                    JOptionPane.showMessageDialog(null, "没有查询到相关消息！");
                }
            }
        });
        reset.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                cnotext.setText("");
                snotext.setText("");
            }
        });
    }

    public void refreshScore()
    {
        // TODO 自动生成的方法存根
        new Score();
        SystemUI.scrollPane4.setViewportView(Score.table4);
    }



    public void totalrank()
    {
        // TODO 自动生成的方法存根
        JFrame jf = new JFrame("总分排名");
        jf.setVisible(true);
        jf.setSize(250, 300);
        jf.setLocation(500,300);
        JScrollPane js=new JScrollPane();
        jf.add(js,BorderLayout.CENTER);
        columnNames=new Vector<Object>();
        //设置列名
        columnNames.add("名次");
        columnNames.add("学号");
        columnNames.add("总分");
        //rowData = new Vector<Object>();
        try
        {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //得到连接
            ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
                    +userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
            ps=ct.prepareStatement("select studentid,sum(score) sum_grade from score group by score.studentid having count(courseid)>=1 order by sum_grade desc");
            rs=ps.executeQuery();
            int rank=0;
            rowData.clear();
            while(rs.next())
            {
                //rowData可以存放多行
                rank++;
                Vector<Object> hang=new Vector<Object>();
                hang.add(rank);
                hang.add(rs.getString(1));
                hang.add(rs.getString(2));
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
        table7 = new JTable(rowData,columnNames)
        {
            private static final long serialVersionUID = 3701216243676371199L;
            public boolean isCellEditable(int row, int column)
            {
                return false;   //表格不允许被编辑
            }
        };
        table7.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
        tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
        table7.setDefaultRenderer(Object.class, tcr);
        js.setViewportView(table7);

    }
}


