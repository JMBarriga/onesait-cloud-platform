package com.minsait.onesait.platform.controlpanel.controller.reports;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.minsait.onesait.platform.config.model.Report;
import com.minsait.onesait.platform.config.services.reports.ReportService;
import com.minsait.onesait.platform.reports.model.ReportDto;
import com.minsait.onesait.platform.reports.service.GenerateReportService;
import com.minsait.onesait.platform.reports.type.ReportTypeEnum;

import net.sf.jasperreports.engine.JRException;

@Controller
public class ReportDownloadController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private GenerateReportService reportBuilderService;
	
	@GetMapping(value = "/download/report/{id}", produces = { MediaType.APPLICATION_PDF_VALUE })
    public void download(HttpServletResponse response, @PathVariable("id") Long id) throws JRException, IOException {
		
		Report entity = reportService.findById(id);
		
		if (entity.getFile() == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		ReportDto reportData = reportBuilderService.generate(entity, ReportTypeEnum.PDF);
		
		// Hace falta una cookie para que el plugin ajax funcione correctamente y retire la animación de loading...
		Cookie cookie = new Cookie("fileDownload", "true");
		cookie.setPath("/");
		response.addCookie(cookie);
			
		//Preparar response
		response.setHeader("Cache-Control", "max-age=60, must-revalidate");
		response.setHeader("Content-disposition", "attachment; filename=" + reportData.getName() + "." + ReportTypeEnum.PDF.extension());
		response.setContentType(ReportTypeEnum.PDF.contentType());
		response.setContentLength(reportData.getContent().length);
					
		//Enviar fichero al navegador
		response.getOutputStream().write(reportData.getContent());
		response.flushBuffer();
	}
}
