    package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Estado;
import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EstadoDAOJPAImplementation implements IEstadoJPA{
    
    @Autowired
    private EntityManager entityManager;    
     
    @Override
    public Resultado GetByPais(int IdPais) {
        Resultado resultado = new Resultado();
        
        try {
            TypedQuery<Estado> consulta = entityManager.createQuery(" FROM Estado e WHERE e.Pais.IdPais = :IdPais", Estado.class);
            consulta.setParameter("IdPais", IdPais);
            
            List<Estado> estados =  consulta.getResultList();
            resultado.objects = new ArrayList<>();
            for (Estado estado : estados) {
                resultado.objects.add(estado);
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
    public Resultado GetById(int IdEstado) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
