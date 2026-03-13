package com.digis01.MFProgramacionNCapasMaven.DAO;

import com.digis01.MFProgramacionNCapasMaven.JPA.Resultado;

public interface IPaisJPA {
    Resultado GetALL();
    
    Resultado GetByID(int IdPais);
}
