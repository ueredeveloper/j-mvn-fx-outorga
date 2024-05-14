package requerimentos;

import java.util.ArrayList;
import java.util.List;

public class CheckList {
	
	private String clTipoOutorga;
	private String clSubtipoOutorga;
	private List<String> clItens = new ArrayList<String>();
	
	public String getClTipoOutorga() {
		return clTipoOutorga;
	}
	public void setClTipoOutorga(String clTipoOutorga) {
		this.clTipoOutorga = clTipoOutorga;
	}
	public String getClSubtipoOutorga() {
		return clSubtipoOutorga;
	}
	public void setClSubtipoOutorga(String clSubtipoOutorga) {
		this.clSubtipoOutorga = clSubtipoOutorga;
	}
	public List<String> getClItens() {
		return clItens;
	}
	public void setClItens(List<String> clItens) {
		this.clItens = clItens;
	}

	
	
		

}
