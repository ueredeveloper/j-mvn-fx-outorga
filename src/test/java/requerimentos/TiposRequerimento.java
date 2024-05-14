package requerimentos;

import entidades.Interferencia;
import entidades.Subterranea;
import entidades.SubtipoOutorga;
import entidades.TipoOutorga;

public class TiposRequerimento {

	public static void main(String[] args) {

/*
		outorga - REQUERIMENTO DE OUTORGA DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA
		registro - REQUERIMENTO DE OUTORGA DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA	
		renovação - REQUERIMENTO DE RENOVAÇÃO DE OUTORGA DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA					
		modificação REQUERIMENTO DE MODIFICAÇÃO DE OUTORGA DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA				
		transferência - REQUERIMENTO DE TRANSFERÊNCIA DE OUTORGA DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA				
		revogação - REQUERIMENTO DE REVOGAÇÃO/SUSPENSÃO DE OUTORGA DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA			

		outorga prévia - REQUERIMENTO DE OUTORGA PRÉVIA PARA PERFURAÇÃO DE POÇO							
		renovação prévia - REQUERIMENTO DE RENOVAÇÃO DE OUTORGA PRÉVIA PARA PERFURAÇÃO DE POÇO					
		modificação prévia REQUERIMENTO DE MODIFICAÇÃO DE OUTORGA PRÉVIA PARA PERFURAÇÃO DE POÇO				
		revogação suspensão prévia - REQUERIMENTO DE REVOGAÇÃO/SUSPENSÃO DE OUTORGA PRÉVIA PARA PERFURAÇÃO DE POÇO
		
		Outorga de direito de uso de recursos hídricos	
		Registro de Uso			
		Renovação outorga de direito de uso de recursos hídricos	
		Modificação outorga de direito de uso de recursos hídricos																		
		Transferência outorga de direito de uso de recursos hídricos																		
		Revogação/Suspensão outorga de direito de uso de recursos 																		
													
															
		Outorga prévia para perfuração de poço																		
		Renovação de outorga prévia para perfuração de poço																		
			Modificação de outorga prévia para perfuração de poço																		
					Revogação/Suspensão de outorga prévia para perfuração																		
					

		// tipo outorga outorga outorga prévia registro
		// subtipo outorga renovação modificação transferência suspensão/revogação null
		
		*/
		
		Interferencia interferencia = new Subterranea();
		TipoOutorga to = new TipoOutorga(1, "Registro");
		SubtipoOutorga so = new SubtipoOutorga(1, "Transferência");
		interferencia.setInterTipoOutorgaFK(to);
		interferencia.setInterSubtipoOutorgaFK(so);
		
		String strTipo = interferencia.getInterTipoOutorgaFK().getTipoOutorgaDescricao();
		String strSubTipo = interferencia.getInterSubtipoOutorgaFK().getSubtipoOutorgaDescricao();
		String strTipoUPPERCASE = "";
		String strSubTipoLOWERCASE = "";
		
		if (strSubTipo.equals("")) {
			
			if (interferencia.getInterTipoOutorgaFK().getTipoOutorgaDescricao().equals("Outorga")) {
				
				strTipoUPPERCASE 	= strTipo.toUpperCase() + " DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA";
				strSubTipoLOWERCASE = strTipo.toLowerCase() + " de direito de uso de recursos hídricos";
			}
			else if (interferencia.getInterTipoOutorgaFK().getTipoOutorgaDescricao().equals("Registro")) {
				strTipoUPPERCASE 	= strTipo.toUpperCase() + " DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA";
				strSubTipoLOWERCASE = strTipo.toLowerCase() + " de uso";
			}
			else {
				strTipoUPPERCASE 	= strTipo.toUpperCase() + " PARA PERFURAÇÃO DE POÇO";
				strSubTipoLOWERCASE = strTipo.toLowerCase() + " para perfuração";
			}
			
		} else {
			
			if (interferencia.getInterTipoOutorgaFK().getTipoOutorgaDescricao().equals("Outorga")) {
				
				strTipoUPPERCASE 	= strSubTipo.toUpperCase() + " DE " + strTipo.toUpperCase() + " DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA";
				strSubTipoLOWERCASE = strSubTipo.toLowerCase() + " de " + strTipo.toLowerCase() + " de direito de uso de recursos hídricos";

			}
			else if (interferencia.getInterTipoOutorgaFK().getTipoOutorgaDescricao().equals("Registro")) {
				strTipoUPPERCASE 	= strSubTipo.toUpperCase() + " DE " + strTipo.toUpperCase() + " DE DIREITO DE USO DE ÁGUA SUBTERRÂNEA";
				strSubTipoLOWERCASE = strSubTipo.toLowerCase() + " de " + strTipo.toLowerCase() + " de uso";
			}
			else {
				strTipoUPPERCASE 	= strSubTipo.toUpperCase() + " DE " + strTipo.toUpperCase() + " PARA PERFURAÇÃO DE POÇO";
				strSubTipoLOWERCASE = strSubTipo.toLowerCase() + " de " + strTipo.toLowerCase() + " para perfuração de poço";
			}
		}
		
				
	
		
		System.out.println("REQUERIMENTO DE " + strTipoUPPERCASE);
		
		System.out.println(strSubTipoLOWERCASE);
		

	}

}
