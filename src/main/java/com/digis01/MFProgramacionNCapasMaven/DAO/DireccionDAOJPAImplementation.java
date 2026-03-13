package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Colonia;
import com.digis01.MFProgramacionNCapasMaven.JPA.Direccion;
import com.digis01.MFProgramacionNCapasMaven.JPA.Usuario;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionDAOJPAImplementation implements IDireccionJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    @Transactional
    public Resultado Add(Direccion direccion, int IdUsuario) {
        Resultado resultado = new Resultado();
        try {
            
            Usuario usuario = entityManager.find(Usuario.class, IdUsuario);
            direccion.Usuario = usuario;
            
            entityManager.persist(direccion);
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }

    @Override
    public Resultado GetById(int IdDireccion) {
        Resultado resultado = new Resultado();
        try {
            
            Direccion direccion = entityManager.find(Direccion.class, IdDireccion);

            resultado.object = direccion;
            
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
    public Resultado Update(Direccion direccion, int IdDireccion) {
        Resultado resultado = new Resultado();
        try {
            
            Direccion direccionJPA = entityManager.find(Direccion.class, IdDireccion);
            direccionJPA.setCalle(direccion.getCalle());
            direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(direccion.getNumeroExterior());
            direccionJPA.Colonia = new Colonia();
            direccionJPA.Colonia.setIdColonia(direccion.Colonia.getIdColonia());
            
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
    public Resultado Delete(int IdDireccion) {
        Resultado resultado = new Resultado();
        
        try {
            
            Direccion direccionJPA = entityManager.find(Direccion.class, IdDireccion);
            entityManager.remove(direccionJPA);
            
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }
    
}
    