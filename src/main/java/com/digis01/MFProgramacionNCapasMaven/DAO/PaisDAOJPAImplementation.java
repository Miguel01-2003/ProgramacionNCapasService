package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Pais;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDAOJPAImplementation implements IPaisJPA{

    
    @Autowired
    private EntityManager entityManager;    
    
    @Override
    public Resultado GetALL() {
        Resultado resultado = new Resultado();
        
        try {
            
            List<Pais> paises = entityManager.createQuery("FROM Pais", Pais.class).getResultList();
            
            resultado.objects = new ArrayList<>();
            
            for (Pais pais : paises) {
                resultado.objects.add(pais);
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
    public Resultado GetByID(int IdPais) {
        Resultado resultado = new Resultado();
        
        try {
            
            resultado.object = entityManager.find(Pais.class, IdPais);
            resultado.correcto = true;
            
        } catch (Exception ex) {
            resultado.correcto = false;
            resultado.mensajeError = ex.getLocalizedMessage();
            resultado.ex = ex;
        }
        
        
        
        return resultado;
    }
    
}
