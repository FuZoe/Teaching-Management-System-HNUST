Teaching Management System By Java Link Database

项目简介：每学期学校都要开设一定的课程，提供给学生根据自己的情况来选择。最后的选课结果给出学生的课表，且把选课名单提供给任课教师进行期末成绩的评定。   
Project Introduction: Every semester, the school will offer certain courses for students to choose according to their own situation.    The final course selection result will be given to the students' class schedule, and the course selection list will be provided to the teachers for the final grade assessment.

使用须知：  
本代码使用Java运行，运行原理是使用JDBC组件链接本机数据库。  
在运行程序前，请先在jiaoxue/src这个目录下找到Login.java文件，然后在文件中找到Jdbc这个类，找到以下两行代码:  
		String name = "root";  
		String passwd = "123456";  
将name和passwd的值修改为运行代码的电脑上的数据库名称和数据库密码。  

Notes:  
This code is run in Java, and the operating principle is to use the JDBC component to connect to the local database.  
Before running the program, please find the Login.java file in the jiaoxue/src directory, then find the Jdbc class in the file and find the following two lines of code:  
		String name = "root";  
		String passwd = "123456";  
Change the values ​​of 'name' and 'passwd' to the database name and database password on the computer running the code.  
