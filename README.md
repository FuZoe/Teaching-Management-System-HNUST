Teaching Management System By Java Link Database

项目简介：每学期学校都要开设一定的课程，提供给学生根据自己的情况来选择。最后的选课结果给出学生的课表，且把选课名单提供给任课教师进行期末成绩的评定。   
Project Introduction: Every semester, the school will offer certain courses for students to choose according to their own situation.    The final course selection result will be given to the students' class schedule, and the course selection list will be provided to the teachers for the final grade assessment.

使用须知：  
本代码使用Java运行，运行原理是使用JDBC组件链接本机数据库。  
在运行程序前，请完成以下两个步骤
1.先在jiaoxue/src这个目录下中所有的.java文件，找到以下两行代码:  
		String name = "root";  
		String passwd = "123456";  
将name和passwd的值全部修改（注意：是每一处都需要修改）为运行代码的电脑上的数据库名称和数据库密码。  
2.在本地数据库 建立一个名为infodb的数据库，然后按照以下7张图片建立表格
![数据库总览](./img/01.png)
![enter image description here](./img/02.png)
![enter image description here](./img/03.png)
![enter image description here](./img/04.png)
![enter image description here](./img/05.png)
![enter image description here](./img/06.png)
![enter image description here](./img/07.png)

Notes:
This code is run using Java, and the operating principle is to use the JDBC component to connect to the local database.
Before running the program, please complete the following two steps
1. First, find the following two lines of code in all .java files in the jiaoxue/src directory:
String name = "root";
String passwd = "123456";
Change all the values ​​of name and passwd (note: every place needs to be changed) to the database name and database password on the computer running the code.
2. Create a database named infodb in the local database, and then create a table according to the following 7 pictures(Above).
