package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import com.digis01.MFProgramacionNCapasMaven.JPA.Usuario;
import java.util.List;

public interface IUsuarioJPA {
    Resultado GetAll();
    
    Resultado Add(Usuario usuario);
    
    Resultado GetById(int idusuario);
    
    Resultado Delete(int idusuario);
    
    Resultado Update(Usuario usuario);
    
    Resultado UpdateImagen(String imagen, int IdUsuario);
    
    Resultado UpdateStatus(int idUsuario, int status); 
    
    Resultado BuscarUsuario(Usuario usuario);
    
    Resultado AddAll(List<Usuario> usuarios);
}
