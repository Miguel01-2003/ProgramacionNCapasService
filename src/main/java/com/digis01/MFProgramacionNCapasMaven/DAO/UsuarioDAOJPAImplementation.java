package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.MFProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import com.digis01.MFProgramacionNCapasMaven.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOJPAImplementation implements IUsuarioJPA {   
    
    @Autowired
    private EntityManager entityManager;    
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Resultado GetAll() {
        Resultado resultado = new Resultado();
        try {            
            
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuarios = queryUsuario.getResultList(); 
            
            resultado.objects = new ArrayList<>();
            
            for (Usuario usuario : usuarios) { 
                resultado.objects.add(usuario);    
            }
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        return resultado;
    }

    @Override
    @Transactional
    public Resultado Add(Usuario usuario) {
        Resultado resultado = new Resultado();
        Direccion direccion = new Direccion();
        try {
            direccion = usuario.Direcciones.get(0);
            direccion.Usuario = usuario;
            
            entityManager.persist(usuario);
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    public Resultado GetById(int idusuario) {
        Resultado resultado = new Resultado();
        try {
            
            Usuario usuario = entityManager.find(Usuario.class, idusuario);
            
            resultado.object = usuario;
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = true;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    @Transactional
    public Resultado Delete(int idusuario) {
        Resultado resultado = new Resultado ();
        
        try {
            
            Usuario usuario = entityManager.find(Usuario.class, idusuario);
            entityManager.remove(usuario);
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError= ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        
        return resultado;
    }

    @Override
    @Transactional
    public Resultado Update(Usuario usuario, int IdUsuario) {
        Resultado resultado = new Resultado();
        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, IdUsuario);
            usuarioJPA.setNombre(usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
            usuarioJPA.setSexo(usuario.getSexo());
            usuarioJPA.setCURP(usuario.getCURP());
            usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioJPA.setUserName(usuario.getUserName());
            usuarioJPA.setEmail(usuario.getEmail());
            usuarioJPA.setTelefono(usuario.getTelefono());
            usuarioJPA.setCelular(usuario.getCelular());
            
            Rol rol = entityManager.find(Rol.class, usuario.Rol.getIdRol());
            
            usuarioJPA.Rol = rol;
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    @Transactional
    public Resultado UpdateImagen(String imagen, int IdUsuario) {
        Resultado resultado = new Resultado();
        
        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, IdUsuario);
            usuarioJPA.setImagen(imagen);
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    @Transactional
    public Resultado UpdateStatus(int idUsuario, int status) {
        Resultado resultado = new Resultado();
        try { 
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);
            usuarioJPA.setStatus(status);
            
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;        
    }

    @Override
    public Resultado BuscarUsuario(Usuario usuario) {
        Resultado resultado = new Resultado();
        
        try {
            TypedQuery<Usuario> consulta = entityManager.createQuery("FROM Usuario WHERE LOWER (Nombre) LIKE LOWER('%'||:pNombre||'%')"
                    + "AND LOWER (ApellidoPaterno) LIKE LOWER ('%'||:pApellidoPaterno||'%')"
                    + "AND (LOWER(ApellidoMaterno) LIKE LOWER('%'||:pApellidoMaterno||'%') OR ApellidoMaterno IS NULL)"
                    + "AND (:pIdRol = 0 OR Rol.IdRol = :pIdRol)", Usuario.class);

            consulta.setParameter("pNombre", usuario.getNombre());
            consulta.setParameter("pApellidoPaterno", usuario.getApellidoPaterno());
            consulta.setParameter("pApellidoMaterno", usuario.getApellidoMaterno());
            consulta.setParameter("pIdRol", usuario.Rol.getIdRol());
            
            resultado.objects = new ArrayList<>(consulta.getResultList());
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    public Resultado AddAll(List<Usuario> usuarios) {
        Resultado resultado = new Resultado();
        
        try {
            
            for (Usuario usuario : usuarios) {
                Direccion direccion = new Direccion();
                direccion = usuario.Direcciones.get(0);
                direccion.Usuario = usuario;
                entityManager.persist(usuario);
            }
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        
        return resultado;
    }
    
}
