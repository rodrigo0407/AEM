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
import classes.Aluno;
import javax.swing.JOptionPane;

public class RepAlunos {
    
    Connection con;

    public boolean inserir(Aluno aluno){
        
        con = ConexaoMySql.getConexao(); 
        
        String sql = "insert into aluno (nome,"
                 + "cpf,email,endereco,data_nascimento,responsavel) values "
                 + "(?,?,?,?,?,?)";
         try{
             con.setAutoCommit(false);
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setString(1, aluno.getNome());
             stmt.setString(2, aluno.getCpf());
             stmt.setString(3, aluno.getEmail());
             stmt.setString(4, aluno.getEndereco());
             stmt.setString(5, aluno.getDatanascimento());
             stmt.setString(6, aluno.getResponsavel());
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
    
  public List<Aluno> retornar(){
      
      con = ConexaoMySql.getConexao();
      List<Aluno> alunos = new ArrayList<>();
      
      String sql = "select * from aluno order by idaluno desc";
      
      try{
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              
              Aluno aluno = new Aluno();
              
              aluno.setIdaluno(rs.getInt("idaluno"));
              aluno.setNome(rs.getString("nome"));
              aluno.setCpf(rs.getString("cpf"));
              aluno.setDatanascimento(rs.getString("data_nascimento"));
              aluno.setEndereco(rs.getString("endereco"));
              aluno.setEmail(rs.getString("email"));
              aluno.setResponsavel(rs.getString("responsavel"));
              
              alunos.add(aluno);
          }            
      }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, ex.getMessage());
          return null;
      }
      
      ConexaoMySql.fecharConexao();
      
      return alunos;
  }  
  
      public boolean atualizar(Aluno aluno) {

        con = ConexaoMySql.getConexao();
        String sql = "update aluno set nome = ?, "
                + "cpf = ?, data_nascimento = ?,endereco = ?,email = ?, responsavel = ? where idaluno = ?";
        try {
            con.setAutoCommit(false);
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getDatanascimento());
            stmt.setString(4, aluno.getEndereco());
            stmt.setString(5, aluno.getEmail());
            stmt.setString(6, aluno.getResponsavel());
            stmt.setInt(7, aluno.getIdaluno());
             
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
      
      
      String sql = "SELECT COUNT(*) AS TOTAL FROM aluno";
      
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
    
  
  public List<Aluno> pesquisa(String valor){
      
      con = ConexaoMySql.getConexao();
      List<Aluno> alunos = new ArrayList<>();
      
      String sql = "select * from aluno where nome like '"+valor+"%'";
      
      
      try{
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              
              Aluno aluno = new Aluno();
              
              aluno.setIdaluno(rs.getInt("idaluno"));
              aluno.setNome(rs.getString("nome"));
              aluno.setCpf(rs.getString("cpf"));
              aluno.setDatanascimento(rs.getString("data_Nascimento"));
              aluno.setEndereco(rs.getString("endereco"));
              aluno.setResponsavel(rs.getString("Responsavel"));
              aluno.setEmail(rs.getString("email"));
              
              alunos.add(aluno);
          }            
      }catch(SQLException ex){
          return null;
      }
      
      ConexaoMySql.fecharConexao();
      
      return alunos;
  }  
    
  
  public boolean excluir(int id){
      
      con = ConexaoMySql.getConexao();
      String sql = "delete from aluno where idaluno = ?";
      
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
