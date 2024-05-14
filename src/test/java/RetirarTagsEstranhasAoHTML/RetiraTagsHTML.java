package RetirarTagsEstranhasAoHTML;

public class RetiraTagsHTML {

	public static void main(String[] args) {
		
		
		String strTagsInterferencia [] = {
				
				"inter_tipo_outorga_tag",
				"inter_tipo_poco_tag",
				"inter_subsistema_tag",
				"inter_uh_tag",
				"inter_bacia_tag",
				"inter_lat_tag",
				"inter_lon_tag",
				"inter_prof_tag",
				"inter_nivel_est_tag",
				"inter_niv_din_tag",
				"inter_vazao_tag"
				
		};

		String str = "         <inter_subsistema_tag>         R3/Q3         </inter_subsistema_tag>, unidade hidrográfica         <inter_uh_tag>         Ribeirão Sonhém        </inter_uh_tag>.</p>        >";

		for (String s : strTagsInterferencia) {
			
			str = str.replace("<" + s + ">", "");
			str = str.replace("</" + s + ">", "");
			
		}

		
		
		System.out.println(str);
		
	}

}
