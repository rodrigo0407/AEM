package repositorio;

import com.mysql.jdbc.MySQLConnection;
import conexao.ConexaoMySql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import classes.Disciplina;
import javax.swing.JOptionPane;

public class RepDisciplina {
    
    Connection con;

    public boolean inserir(Disciplina disciplina){
        
        con = ConexaoMySql.getConexao(); 
        
        String sql = "insert into disciplina (serie,"
                 + "turma,turno,alunoid,escolaid,idprofessor) values "
                 + "(?,?,?,?,?,?)";
         try{
             con.setAutoCommit(false);
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setString(1, disciplina.getSerie());
             stmt.setString(2, disciplina.getTurma());
             stmt.setString(3, disciplina.getTurno());
             stmt.setInt(4, disciplina.getAlunoid());
             stmt.setInt(5, disciplina.getEscolaid());
             stmt.setInt(6, disciplina.getProfessorid());
             
             stmt.execute();
             con.commit();
             ConexaoMySql.fecharConexao();
             
            return true;
         }catch(SQLException ex){
             try{
                 con.rollback();
                 System.err.println(ex.getMessage());
                 JOptionPane.showMessageDialog(null, ex.getMessage());
                 return false;
             }catch(SQLException exSql){
                 System.err.println(exSql.getMessage());
             }
         }
         
       return true;
    }
    
  public List<Disciplina> retornar(){
      
      con = ConexaoMySql.getConexao();
      List<Disciplina> disciplinas = new ArrayList<>();
      
      String sql = "select * from disciplina order by idDisciplina desc";
      
      try{
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              
              Disciplina disciplina = new Disciplina();
              
              disciplina.setIddisciplina(rs.getInt("idDisciplina"));
              disciplina.setSerie(rs.getString("serie"));
              disciplina.setTurma(rs.getString("turma"));
              disciplina.setTurno(rs.getString("turno"));
              disciplina.setEscolaid(rs.getInt("escolaid"));
              disciplina.setAlunoid(rs.getInt("alunoid"));
              disciplina.setProfessorid(rs.getInt("idprofessor"));
              
              disciplinas.add(disciplina);
          }            
      }catch(SQLException ex){
          return null;
      }
      
      ConexaoMySql.fecharConexao();
      
      return disciplinas;
  }  
  
      public boolean atualizar(Disciplina disciplina) {

        con = ConexaoMySql.getConexao();
        String sql = "update disciplina set serie = ?, "
                + "turma = ?, turno = ?,alunoid = ?,idprofessor = ?, escolaid = ? where idDisciplina = ?";
        try {
            con.setAutoCommit(false);
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, disciplina.getSerie());
            stmt.setString(2, disciplina.getTurma());
            stmt.setString(3, disciplina.getTurno());
            stmt.setInt(4, disciplina.getAlunoid());
            stmt.setInt(5, disciplina.getEscolaid());
            stmt.setInt(6, disciplina.getProfessorid());
            stmt.setInt(7, disciplina.getIddisciplina());
             
            stmt.execute();

            con.commit();
            ConexaoMySql.fecharConexao();

            return true;

        } catch (SQLException ex) {
            try {
                con.rollback();
                System.err.println(ex);
                
                
                
                return false;
            } catch (SQLException ex1) {
                System.err.println(ex1);
            }

            return false;
        }

    }  
    public int  retornaTotalAluno(){
      
          int retorno = 0;
      con = ConexaoMySql.getConexao();
      
      
      String sql = "SELECT COUNT(*) AS TOTAL FROM disciplina";
      
      try{
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              retorno = rs.getInt("total");
          }            
      }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, ex.getMessage());
          return retorno;
      }
      
      ConexaoMySql.fecharConexao();
      
      return retorno;
  }  
    
  public List<Disciplina> pesquisa(String valor){
      
      con = ConexaoMySql.getConexao();
      List<Disciplina> disciplinas = new ArrayList<>();
      
      String sql = "select * from disciplina where turma like '"+valor+"%'";
      
      
      try{
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              
              Disciplina disciplina = new Disciplina();
              
              disciplina.setIddisciplina(rs.getInt("idDisciplina"));
              disciplina.setSerie(rs.getString("serie"));
              disciplina.setTurma(rs.getString("turma"));
              disciplina.setTurno(rs.getString("turno"));
              disciplina.setAlunoid(rs.getInt("alunoid"));
              disciplina.setEscolaid(rs.getInt("escolaid"));
              disciplina.setProfessorid(rs.getInt("idprofessor"));
              
              disciplinas.add(disciplina);
          }            
      }catch(SQLException ex){
          return null;
      }
      
      ConexaoMySql.fecharConexao();
      
      return disciplinas;
  }  
    
  
  public boolean excluir(int id){
      
      con = ConexaoMySql.getConexao();
      String sql = "delete from disciplina where iddisciplina = ?";
      
      try{
          
          con.setAutoCommit(false);
          PreparedStatement stmt = con.prepareStatement(sql);
          
          stmt.setInt(1, id);
          
          stmt.execute();
          con.commit();
          ConexaoMySql.fecharConexao();
      
          return true;   
      }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, ex.getMessage());
          return false;
      }
        
  }
    
    
}
