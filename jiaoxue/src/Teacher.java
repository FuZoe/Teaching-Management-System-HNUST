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
import java.util.Date;


public class Teacher
{
	Vector<Object> columnNames;

	private Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
	static JTable table1=null;
	//定义数据库需要的全局变量
	PreparedStatement ps=null;
	Connection ct=null;
	ResultSet rs=null;//将对数据库查询的结果保存进resultset的对象rs中
	public Teacher()
	{
		columnNames=new Vector<Object>();
		//设置列名
		columnNames.add("工号");
		columnNames.add("姓名");
		columnNames.add("部门");
		//rowData可以存放多行,开始从数据库里取
		try
		{
			String userMySql="root";
			String passwordMySql="150848";
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			//得到连接，并获取连接对象ct
			ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
					+userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");

			ps=ct.prepareStatement("select * from teacher");//向mysql发送动态的sql语句

			rs=ps.executeQuery();//使用select语句时用executeQuery执行，executeupdate用于执行insert，update，delete，execute用于返回多个结果集的语句。

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
		table1=new JTable(rowData,columnNames)
		{
			private static final long serialVersionUID=-3229560868878458304L;
			public boolean isCellEditable(int row,int column)
			{
				return false;
			}
		};
		table1.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
		table1.setDefaultRenderer(Object.class, tcr);
	}

	//添加教师的信息；
	public void addTeacher()
	{
		// TODO 自动生成的方法存根
		SystemUI.unclickable();
		JFrame AddTea = new JFrame("添加教师信息");
		AddTea.setSize(250, 270);
		AddTea.setLocation(600, 300);
		JPanel addtea = new JPanel();
		JLabel teacherid = new JLabel("工号");
		JLabel teachername = new JLabel("姓名");
		JLabel teacherdepartment = new JLabel("部门");
		JTextField teacheridtext  = new JTextField();
		JTextField teachernametext = new JTextField();
		JTextField teacherdepartmenttext  = new JTextField();
		JButton ok = new JButton("确定");
		JButton reset = new JButton("重置");

		addtea.setLayout(null);
		teacherid.setBounds(5,5,70,20);
		teacheridtext.setBounds(80,5,120,20);
		teachername.setBounds(5,30,70,20);
		teachernametext.setBounds(80,30,120,20);
		teacherdepartment.setBounds(5,60,70,20);
		teacherdepartmenttext.setBounds(80,60,120,20);

		ok.setBounds(50,190,60,20);
		reset.setBounds(130,190,60,20);
		addtea.add(teacherid);
		addtea.add(teacheridtext);
		addtea.add(teachername);
		addtea.add(teachernametext);
		addtea.add(teacherdepartment);
		addtea.add(teacherdepartmenttext);
		addtea.add(ok);
		addtea.add(reset);

		AddTea.add(addtea);
		AddTea.setVisible(true);

		AddTea.addWindowListener(new WindowAdapter()
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

					String  name = teachernametext.getText();
					String department = teacherdepartmenttext.getText();
					int id = Integer.valueOf(teacheridtext.getText()).intValue();

					//加载驱动
					Class.forName("com.mysql.jdbc.Driver");
					//得到连接
					ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
							+userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
					ps=ct.prepareStatement("insert into teacher values('"+id+"','"+name+"','"+department+"')");
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "添加成功！");
					AddTea.dispose();
					SystemUI.clickable();
					addTeacher();

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

				teacheridtext.setText("");
				teachernametext.setText("");
				teacherdepartmenttext.setText("");

			}
		});
	}
	//修改教师的信息；
	public void updateTeacher()
	{
		// TODO 自动生成的方法存根
		SystemUI.unclickable();
		JFrame UpdateTea = new JFrame("修改教师信息");
		UpdateTea.setSize(250, 290);
		UpdateTea.setLocation(600, 300);
		JPanel updatetea = new JPanel();
		JLabel upid = new JLabel("要修改的工号");
		JLabel upname = new JLabel("要改成的姓名");
		JLabel updepartment = new JLabel("要改成的部门");
		JTextField upidtext = new JTextField();
		JTextField upnametext = new JTextField();
		JTextField updepartmenttext  = new JTextField();
		JButton ok = new JButton("确定");
		JButton reset = new JButton("重置");

		updatetea.setLayout(null);

		upid.setBounds(5,30,100,20);
		upidtext.setBounds(110,30,120,20);
		upname.setBounds(5,60,100,20);
		upnametext.setBounds(110,60,120,20);
		updepartment.setBounds(5,90,100,20);
		updepartmenttext.setBounds(110,90,120,20);
		ok.setBounds(50,210,60,20);
		reset.setBounds(120,210,60,20);

		updatetea.add(upid);
		updatetea.add(upidtext);
		updatetea.add(upname);
		updatetea.add(upnametext);
		updatetea.add(updepartment);
		updatetea.add(updepartmenttext);
		updatetea.add(ok);
		updatetea.add(reset);

		UpdateTea.add(updatetea);
		UpdateTea.setVisible(true);

		UpdateTea.addWindowListener(new WindowAdapter()
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
					//String id = upidtext.getText();
					String name = upnametext.getText();
					String department = updepartmenttext.getText();
					int id = Integer.valueOf(upidtext.getText()).intValue();

					//加载驱动
					Class.forName("com.mysql.jdbc.Driver");
					//得到连接
					ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
							+userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
					ps=ct.prepareStatement("update teacher set teachername ='"+name+"',teacherdepartment ='"+department+"' where teacherid='"+id+"'");
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "修改成功！");
					UpdateTea.dispose();
					SystemUI.clickable();
					addTeacher();
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
				upidtext.setText("");
				upnametext.setText("");
				updepartmenttext.setText("");

			}
		});
	}
	//删除教师信息
	public void deleteTeacher()
	{
		// TODO 自动生成的方法存根
		int row = table1.getSelectedRow();
		if ( row >= 0 )
		{
			String string[] = new String[5];
			string[0] = String.valueOf(table1.getValueAt(row, 0));
			string[1] = String.valueOf(table1.getValueAt(row, 1));
			try
			{
				String userMySql="root";
				String passwordMySql="150848";
				//加载驱动
				Class.forName("com.mysql.jdbc.Driver");
				//得到连接
				ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
						+userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
				ps=ct.prepareStatement("delete from teacher where teacherid='"+string[0]+"' and teachername='"+string[1]+"'");
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null, "删除成功！");
				refreshTeacher();
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


	public void searchTeacher()
	{
		// TODO 自动生成的方法存根
		SystemUI.unclickable();
		JFrame SeaTea = new JFrame("查找教师信息");
		SeaTea.setSize(250, 100);
		SeaTea.setLocation(600, 300);
		JPanel seatea = new JPanel();
		JLabel tno = new JLabel("请输入工号");
		JTextField tnotext  = new JTextField();
		JButton ok = new JButton("确定");
		JButton reset = new JButton("重置");

		seatea.setLayout(null);

		tno.setBounds(5,5,70,20);
		tnotext.setBounds(80,5,120,20);
		ok.setBounds(50,30,60,20);
		reset.setBounds(130,30,60,20);

		seatea.add(tno);
		seatea.add(tnotext);
		seatea.add(ok);
		seatea.add(reset);

		SeaTea.add(seatea);
		SeaTea.setVisible(true);

		SeaTea.addWindowListener(new WindowAdapter()
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
				columnNames.add("工号");
				columnNames.add("姓名");
				columnNames.add("部门");
				String str = tnotext.getText();

				int count=0;

				try
				{
					String userMySql="root";
					String passwordMySql="150848";
					//加载驱动
					Class.forName("com.mysql.jdbc.Driver");
					//得到连接
					ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/infodb?user="
							+userMySql+"&password="+passwordMySql + "&useUnicode=true&characterEncoding=GBK&useSSL=false&serverTimezone=GMT");
					if(!tnotext.getText().trim().equals(""))
					{
						ps=ct.prepareStatement("select * from teacher where teacherid='"+str+"'");

						rs=ps.executeQuery();
					}
					if(tnotext.getText().trim().equals(""))
					{
						ps=ct.prepareStatement("select * from teacher ");
						rs=ps.executeQuery();
						JOptionPane.showMessageDialog(null, "请输入查询信息！");
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
					table1 = new JTable(rowData,columnNames)
					{

						private static final long serialVersionUID = -1545099432791772807L;

						public boolean isCellEditable(int row, int column)
						{
							return false;   //表格不允许被编辑
						}
					};
					table1.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
					DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
					tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
					table1.setDefaultRenderer(Object.class, tcr);
					SystemUI.scrollPane1.setViewportView(table1);
					SeaTea.dispose();
					SystemUI.clickable();
				}
				if(count==0)
				{
					JOptionPane.showMessageDialog(null, "查无此人！");
				}
			}
		});
	}
	public void refreshTeacher()
	{
		// TODO 自动生成的方法存根
		new Teacher();
		SystemUI.scrollPane1.setViewportView(Teacher.table1);
	}
}

