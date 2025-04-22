package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Ausencia;
import com.flybook.librovuelo.model.PedidoDiasLibres;
import com.flybook.librovuelo.model.ProgramacionTripulante;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.ExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    private void setHeaderCellStyle(CellStyle style) {
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    private void createHeaderRow(Sheet sheet, String[] headers, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void createValueRow(Row row, String[] values) {
        for (int i = 0; i < values.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(values[i]);
        }
    }

    private String[] getValues(User tripulante) {
        String[] values = {
                String.valueOf(tripulante.getDni()), tripulante.getNombre(), tripulante.getApellido(), String.valueOf(tripulante.getFechaNacimiento()),
                String.valueOf(tripulante.getTelefono()), tripulante.getMail(), tripulante.getSexo(),
                tripulante.getDireccion().getLocalidad().getProvincia().getNombre(),
                tripulante.getDireccion().getCalle() + " " + tripulante.getDireccion().getNumeroDeCalle() + ", " + tripulante.getDireccion().getLocalidad().getNombre(),
                String.valueOf(tripulante.getLegajo()), String.valueOf(tripulante.getTipoCargo()), String.valueOf(tripulante.getFechaIngreso()),
                String.valueOf(tripulante.getGeneracion().getNumero()), tripulante.getDatosContacto().getNombre(), tripulante.getDatosContacto().getApellido(), String.valueOf(tripulante.getDatosContacto().getTelefono()), tripulante.getDatosContacto().getParentezco()
        };
        return values;
    }

    private String[] getHeaders() {
        String[] headers = {
                "DNI", "Nombre", "Apellido", "Fecha de nacimiento", "Telefono", "Mail", "Sexo",
                "Provincia", "Direccion", "Legajo", "Cargo", "Fecha de ingreso", "N° Generacion",
                "Contacto nombre", "Contacto apellido", "Contacto telefono", "Contacto parentezco"
        };
        return headers;
    }

    @Override
    public void exportarInformacionDelTripulanteAExcel(User tripulante, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + tripulante.getApellido() + "_" + tripulante.getLegajo() + ".xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Tripulante");

        CellStyle headerStyle = workbook.createCellStyle();
        setHeaderCellStyle(headerStyle);

        String[] headers = getHeaders();

        createHeaderRow(sheet, headers, headerStyle);

        Row valueRow = sheet.createRow(1);
        String[] values = getValues(tripulante);

        createValueRow(valueRow, values);

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }

        workbook.close();
    }

    @Override
    public void exportarAExcelLaInformacionDeTodos(List<User> tripulantes, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Tripulantes.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Tripulantes");

        CellStyle headerStyle = workbook.createCellStyle();
        setHeaderCellStyle(headerStyle);

        String[] headers = getHeaders();

        createHeaderRow(sheet, headers, headerStyle);

        int rowNum = 1;
        for (User tripulante : tripulantes) {
            Row valueRow = sheet.createRow(rowNum);
            String[] values = getValues(tripulante);

            createValueRow(valueRow, values);

            rowNum++;
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }

        workbook.close();
    }

    //Excel Ausencia
    private String[] getValuesOfAusencia(Ausencia ausencia){
        return new String[]{
                String.valueOf(ausencia.getId()),
                String.valueOf(ausencia.getFechaDesde()),
                String.valueOf(ausencia.getFechaHasta()),
                String.valueOf(ausencia.getTipoAusencia()),
                String.valueOf(ausencia.getUser().getDni()),
                String.valueOf(ausencia.getUser().getLegajo()),
                ausencia.getUser().getNombre(),
                String.valueOf(ausencia.getUser().getGeneracion().getNumero())
        };
    }



    private String[] getHeadersAusencias(){
        return new String [] {"Numero de Ausencia", "Fecha Desde", "Fecha Hasta", "Tipo", "DNI", "Legajo", "Nombre", "Generacion"};
    }

    private void autoSizeColumns(Sheet file, String[] columnsToSize){
        for(int i = 0 ; i<columnsToSize.length ; i++)
            file.autoSizeColumn(i);
    }

    @Override
    public void exportarExcelDeLasAusencias(List<Ausencia> ausencias, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Ausencias.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet file = workbook.createSheet("Ausencias");

        CellStyle style = workbook.createCellStyle();
        setHeaderCellStyle(style);
        String[] headers = getHeadersAusencias();

        createHeaderRow(file,headers,style);

        int rowNumber = 1;
        for(Ausencia ausencia: ausencias){
            Row row = file.createRow(rowNumber++);
            String[] values = getValuesOfAusencia(ausencia);
            createValueRow(row,values);
        }

        autoSizeColumns(file, headers);

        try(OutputStream outputStream = response.getOutputStream()){
            workbook.write(outputStream);
        }

        workbook.close();
    }

    @Override
    public void exportarExcelDeLosPedidosDiasLibres(List<PedidoDiasLibres> pedidoDiasLibres, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=PedidosDiasLibres.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet file = workbook.createSheet("PedidosDiasLibres");

        CellStyle style = workbook.createCellStyle();
        setHeaderCellStyle(style);
        String[] headers = getHeadersPedidoDiasLibres();

        createHeaderRow(file,headers,style);

        int rowNumber = 1;
        for(PedidoDiasLibres pedidoDiaLibre: pedidoDiasLibres){
            Row row = file.createRow(rowNumber++);
            String[] values = getValuesOfPedidoDiaLibre(pedidoDiaLibre);
            createValueRow(row,values);
        }

        autoSizeColumns(file, headers);

        try(OutputStream outputStream = response.getOutputStream()){
            workbook.write(outputStream);
        }

        workbook.close();
    }
    private String[] getHeadersPedidoDiasLibres(){
        return new String [] {"Legajo", "DNI", "Apellido", "Nombre", "Fecha de Solicitud", "Periodo", "Inicio 3 dias"};
    }


    private String[] getValuesOfPedidoDiaLibre(PedidoDiasLibres pedidoDiasLibre){
        return new String[]{
                String.valueOf(pedidoDiasLibre.getUser().getLegajo()),
                String.valueOf(pedidoDiasLibre.getUser().getDni()),
                String.valueOf(pedidoDiasLibre.getUser().getApellido()),
                String.valueOf(pedidoDiasLibre.getUser().getNombre()),
                String.valueOf(pedidoDiasLibre.getFechaSolicitud()),
                String.valueOf(pedidoDiasLibre.getPeriodoDelPedido()),
                String.valueOf(pedidoDiasLibre.getComienzo3DiasLibres()),
        };
    }

    // Excel Programacon de tripulantes


    private String[] getHeadersProgramacionesTripulantes(){
        return new String [] {"Nro","Dia", "Tipo Actividad", "Aeropuerto Origen", "Aeropuerto Destino", "Fecha de Presentacion", "Fecha aterrizaje", "Fecha Despegue", "Agregado al calendario"};
    }

    private String[] getValuesOfProgramacionTripulante(ProgramacionTripulante programacionTripulante){
        return new String[]{
                String.valueOf(programacionTripulante.getId()),
                String.valueOf(programacionTripulante.getDiaActividad()),
                String.valueOf(programacionTripulante.getTipoActividad()),
                String.valueOf(programacionTripulante.getAeropuertoOrigen().getNombre()),
                String.valueOf(programacionTripulante.getAeropuertoDestino().getNombre()),
                String.valueOf(programacionTripulante.getFechaHoraPresentacion()),
                String.valueOf(programacionTripulante.getFechaHoraDestino()),
                String.valueOf(programacionTripulante.getFechaHoraDespegue()),
        };
    }

    @Override
    public void exportarExcelProgramacionTripulantes(List<ProgramacionTripulante> programacionTripulantes, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Ausencias.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet file = workbook.createSheet("Programaciones");

        CellStyle style = workbook.createCellStyle();
        setHeaderCellStyle(style);
        String[] headers = getHeadersProgramacionesTripulantes();

        createHeaderRow(file,headers,style);

        int rowNumber = 1;
        for(ProgramacionTripulante programacionTripulante: programacionTripulantes){
            Row row = file.createRow(rowNumber++);
            String[] values = getValuesOfProgramacionTripulante(programacionTripulante);
            createValueRow(row,values);
        }

        autoSizeColumns(file, headers);

        try(OutputStream outputStream = response.getOutputStream()){
            workbook.write(outputStream);
        }

        workbook.close();
    }
}
