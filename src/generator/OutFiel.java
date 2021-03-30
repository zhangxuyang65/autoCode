package generator;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;

public class OutFiel {
	public static void outFiel1(String [] tables) throws Exception {
		GeneratorFacade g = new GeneratorFacade();
		g.deleteOutRootDir(); // 删除生成器的输出目录
		
		for (String table : tables) {
			g.generateByTable(table, "template-hualala");
		}
		// 打开文件夹
//		Runtime.getRuntime().exec("cmd.exe /c start "+ GeneratorProperties.getRequiredProperty("outRoot"));
	    
	}
}
