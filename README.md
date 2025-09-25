# DBCP(Database Connection Pool) 예제 프로젝트

## 📌 개요
이 프로젝트는 **스프링에서 사용하는 Commons DBCP2/HikariCP**와 달리,  
**Tomcat JDBC Connection Pool(= 톰캣이 제공하는 DBCP)**을 사용하여  
DB 연결을 관리하는 방법을 정리한 예제입니다.  

DBCP를 사용하면 매번 `DriverManager.getConnection()`을 호출하지 않고,  
미리 생성해둔 **Connection Pool**에서 커넥션을 재사용할 수 있어 성능이 향상됩니다.

---

## 🛠️ 환경
- **JDK**: 1.8+
- **Tomcat**: 9.0.108
- **Oracle DB**: ojdbc8
- **JSP/Servlet 프로젝트 구조**

---

## 📂 프로젝트 구조
src/
└─ com.test.java
├─ ConnectionTest.java # DBCP 연결 테스트 (DBUtil.open() 대체)
├─ DBCPTest.java # DAO 기능 테스트
└─ model/
└─ UserDAO.java # DBCP 기반 DAO

WebContent/
└─ WEB-INF/
└─ lib/
└─ ojdbc8.jar # 오라클 JDBC 드라이버

apache-tomcat-9.0.108/
└─ lib/
└─ ojdbc8.jar # JDBC 드라이버 (서버 레벨)



---

## ⚙️ 설정

### 1. 라이브러리 추가
- `WEB-INF/lib/ojdbc8.jar`
- `apache-tomcat-9.0.108/lib/ojdbc8.jar`

### 2. Tomcat 인스턴스 설정
`servers > Tomcat v9.0 Server at localhost-config > context.xml`

```xml
<Context>
    <!-- Oracle DBCP 설정 -->
    <Resource 
        name="jdbc/oracle" 
        auth="Container"
        type="javax.sql.DataSource"
        driverClassName="oracle.jdbc.driver.OracleDriver"
        url="jdbc:oracle:thin:@localhost:1521:xe"
        username="scott"
        password="tiger"
        maxTotal="20"
        maxIdle="10"
        maxWaitMillis="3000"/>
</Context>


💻 코드 예제
ConnectionTest.java

package com.test.java.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
    private DataSource ds;

    public UserDAO() {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/oracle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUsers() {
        String sql = "SELECT id, name FROM tblUser";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                System.out.println(rs.getString("id") + " / " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


DBCPTest.java

package com.test.java;

import com.test.java.model.UserDAO;

public class DBCPTest {
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        dao.getUsers();
    }
}

🚀 실행 방법

Tomcat 실행

context.xml 설정 반영 확인

ConnectionTest → DB 연결 확인

DBCPTest → DAO 실행 확인


