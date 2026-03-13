package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;

public interface IEstadoJPA {
    Resultado GetByPais(int IdPais);
    
    Resultado GetById(int IdEstado);
}
