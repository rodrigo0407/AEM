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
import classes.Escola;
import javax.swing.JOptionPane;

public class RepEscola {
    
    Connection con;

    public boolean inserir(Escola escola){
        
        con = ConexaoMySql.getConexao(); 
        
        String sql = "insert into escola (inep,"
                 + "nome,cnpj,endereco,alunoid) values "
                 + "(?,?,?,?,?)";
         try{
             con.setAutoCommit(false);
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setInt(1, escola.getInep());
             stmt.setString(2, escola.getNome());
             stmt.setString(3, escola.getCnpj());
             stmt.setString(4, escola.getEndereco());
             stmt.setString(5, escola.getAlunoid());
             
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
    
  public List<Escola> retornar(){
      
      con = ConexaoMySql.getConexao();
      List<Escola> escolas = new ArrayList<>();
      
      String sql = "select * from escola order by inep desc";
      
      try{
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              
              Escola escola = new Escola();
              
              escola.setInep(rs.getInt("inep"));
              escola.setNome(rs.getString("nome"));
              escola.setCnpj(rs.getString("cnpj"));
              escola.setEndereco(rs.getString("endereco"));
              escola.setAlunoid(rs.getString("alunoid"));
              
              
              escolas.add(escola);
          }            
      }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, ex.getMessage());
          return null;
      }
      
      ConexaoMySql.fecharConexao();
      
      return escolas;
  }  
  
      public boolean atualizar(Escola escola) {

        con = ConexaoMySql.getConexao();
        String sql = "update escola set nome = ?, "
                + "cnpj = ?, endereco = ?,alunoid = ? where inep = ?";
        try {
            con.setAutoCommit(false);
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, escola.getNome());
            stmt.setString(2, escola.getCnpj());
            stmt.setString(3, escola.getEndereco());
            stmt.setString(4, escola.getAlunoid());
            stmt.setInt(5, escola.getInep());
             
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
      
      
      String sql = "SELECT COUNT(*) AS TOTAL FROM escola";
      
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
    
  
  public List<Escola> pesquisa(String valor){
      
      con = ConexaoMySql.getConexao();
      List<Escola> escolas = new ArrayList<>();
      
      String sql = "select * from escola where nome like '"+valor+"%'";
      
      
      try{
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          while(rs.next()){
              
              Escola escola = new Escola();
              
              escola.setInep(rs.getInt("inep"));
              escola.setNome(rs.getString("nome"));
              escola.setCnpj(rs.getString("cnpj"));
              escola.setEndereco(rs.getString("endereco"));
              escola.setAlunoid(rs.getString("alunoid"));
              
              
              escolas.add(escola);
          }            
      }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, ex.getMessage());
          return null;
      }
      
      ConexaoMySql.fecharConexao();
      
      return escolas;
  }  
    
  
  public boolean excluir(int inep){
      
      con = ConexaoMySql.getConexao();
      String sql = "delete from escola where inep = ?";
      
      try{
          
          con.setAutoCommit(false);
          PreparedStatement stmt = con.prepareStatement(sql);
          
          stmt.setInt(1, inep);
          
          stmt.execute();
          con.commit();
          ConexaoMySql.fecharConexao();
      
          return true;   
      }catch(SQLException ex){
          
          return false;
      }
        
  }
    
    
}
