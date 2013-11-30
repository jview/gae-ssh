package org.esblink.module.auth.domain;

public class ModuleCheckVO extends ModuleVO{
	public ModuleCheckVO(){
		
	}
	public ModuleCheckVO(Module module){
		super(module);		
//		this.checked=module.isChecked();
	}
	
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
