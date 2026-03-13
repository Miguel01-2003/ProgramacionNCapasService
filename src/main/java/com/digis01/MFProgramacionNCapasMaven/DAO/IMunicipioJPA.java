package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;

public interface IMunicipioJPA {
    Resultado GetByEstado(int IdEstado);
    
    Resultado GetById(int IdMunicipio);
}
