package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Ausencia;
import com.flybook.librovuelo.model.PedidoDiasLibres;
import com.flybook.librovuelo.model.ProgramacionTripulante;
import com.flybook.librovuelo.model.User;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ExcelService {
    void exportarInformacionDelTripulanteAExcel(User tripulante, HttpServletResponse response) throws IOException;

    void exportarAExcelLaInformacionDeTodos(List<User> tripulantes, HttpServletResponse response) throws IOException;

    void exportarExcelDeLasAusencias(List<Ausencia> ausencias, HttpServletResponse response) throws IOException;
    void exportarExcelDeLosPedidosDiasLibres(List<PedidoDiasLibres> pedidoDiasLibres, HttpServletResponse response) throws IOException;

    void exportarExcelProgramacionTripulantes(List<ProgramacionTripulante> programacionTripulantes, HttpServletResponse response) throws IOException;
}
