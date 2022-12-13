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
import classes.Professor;
import javax.swing.JOptionPane;

public class RepProfessor {
    
    Connection con;

    public boolean inserir(Professor professor){
        
        con = ConexaoMySql.getConexao(); 
        
        String sql = "insert into Professor (nome,"
                 + "cpf,formacao,endereco,email,ch,idaluno) values "
                 + "(?,?,?,?,?,?,?)";
         try{
             con.setAutoCommit(false);
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setString(1, professor.getNome());
             stmt.setString(2, professor.getCpf());
             stmt.setString(3, professor.getFormacao());
             stmt.setString(4, professor.getEndereco());
             stmt.setString(5, professor.getEmail());
             stmt.setString(6, professor.getCh());
             stmt.setInt(7, professor.getIdaluno());
             
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
    
  public List<Professor> retornar(){
      
      con = ConexaoMySql.getConexao();
      List<Professor> professores = new ArrayList<>();
      
      String sql = "select * from professor order by idprofessor desc";
      
      try{
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              
              Professor professor = new Professor();
              
              professor.setIdprofessor(rs.getInt("idprofessor"));
              professor.setNome(rs.getString("nome"));
              professor.setCpf(rs.getString("cpf"));
              professor.setFormacao(rs.getString("formacao"));
              professor.setEndereco(rs.getString("endereco"));
              professor.setEmail(rs.getString("email"));
              professor.setCh(rs.getString("ch"));
              professor.setIdaluno(rs.getInt("idaluno"));
              
              professores.add(professor);
          }            
      }catch(SQLException ex){
          return null;
      }
      
      ConexaoMySql.fecharConexao();
      
      return professores;
  }  
  
      public boolean atualizar(Professor professor) {

        con = ConexaoMySql.getConexao();
        String sql = "update professor set nome = ?, "
                + "cpf = ?, formacao = ?,endereco = ?,email = ?, ch = ?, idaluno =? where idprofessor = ?";
        try {
            con.setAutoCommit(false);
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getCpf());
            stmt.setString(3, professor.getFormacao());
            stmt.setString(4, professor.getEndereco());
            stmt.setString(5, professor.getEmail());
            stmt.setString(6, professor.getCh());
            stmt.setInt(7, professor.getIdaluno());
            stmt.setInt(8, professor.getIdprofessor());
             
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
      
      
      String sql = "SELECT COUNT(*) AS TOTAL FROM professor";
      
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
    
  public List<Professor> pesquisa(String valor){
      
      con = ConexaoMySql.getConexao();
      List<Professor> professores = new ArrayList<>();
      
      String sql = "select * from professor where nome like '"+valor+"%'";
      
      
      try{
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              
              Professor professor = new Professor();
              
              professor.setIdprofessor(rs.getInt("idprofessor"));
              professor.setNome(rs.getString("nome"));
              professor.setCpf(rs.getString("cpf"));
              professor.setFormacao(rs.getString("formacao"));
              professor.setEndereco(rs.getString("endereco"));
              professor.setEmail(rs.getString("email"));
              professor.setCh(rs.getString("ch"));
              professor.setIdaluno(rs.getInt("idaluno"));
              
              professores.add(professor);
          }            
      }catch(SQLException ex){
          return null;
      }
      
      ConexaoMySql.fecharConexao();
      
      return professores;
  }  
    
  
  public boolean excluir(int id){
      
      con = ConexaoMySql.getConexao();
      String sql = "delete from professor where idprofessor = ?";
      
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
