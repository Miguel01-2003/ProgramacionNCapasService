package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import com.digis01.MFProgramacionNCapasMaven.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class RolDAOJPAImplementation implements IRolJPA{
    
    @Autowired
    private EntityManager entityManager;

    @Override
    public Resultado GetAll() {
        Resultado resultado = new Resultado();
        
        try {
            
            TypedQuery<Rol> queryRol = entityManager.createQuery("FROM Rol", Rol.class);
            List<Rol> roles = queryRol.getResultList();
            
            resultado.objects = new ArrayList<>();
            
            for (Rol rol : roles) {
                resultado.objects.add(rol);
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
    public Resultado GetByID(int IdRol) {
        Resultado resultado = new Resultado();
        
        try {
            
            Rol rol = entityManager.find(Rol.class, IdRol);
            
            resultado.object = rol;
            resultado.correcto = true;
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        return resultado;
    }
    
    
    
}
