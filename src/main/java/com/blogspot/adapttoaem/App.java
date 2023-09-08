package com.blogspot.adapttoaem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.InvalidQueryException;

public class App {

	public static void main( String[] args ) throws IOException {

		try {
			printComponentsUsedPages("/conf/ems-v2/settings/wcm/templates/product-detail-page-template", "/content/adamsrite/us/en");
			//printPropertyValueAndPage("trainingID", "/content/alarm-controls/us/en");
		}catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	private static void printPropertyValueAndPage(String propertyName, String sitePath) {
		String queryString = "SELECT * FROM [nt:unstructured] AS nodes WHERE ISDESCENDANTNODE ([" + sitePath + "]) AND nodes.[" + propertyName + "] IS NOT NULL";
		try {
			NodeIterator itr = JCRUtils.executeSQL2Query(queryString);
			List<String> trainingIDList = new ArrayList<String>();
			Set<String> trainingIDSet = new HashSet<String>();
			System.out.println(itr.getSize());
			while (itr.hasNext()) {
				Node nextNode = itr.nextNode();
				String trainingIDValue = nextNode.getProperty(propertyName).getString();
				trainingIDSet.add(trainingIDValue);
				if (trainingIDList.contains(trainingIDValue)) {
					System.out.println(nextNode.getPath());
				} else {
					trainingIDList.add(trainingIDValue);
				}
			}
			System.out.println(trainingIDSet.size());
		} catch (RepositoryException e) {
			e.printStackTrace();
		}		
	}

	private static void checkDuplicateComponents() throws InvalidQueryException, RepositoryException {
		
		String queryString = "SELECT * FROM [nt:unstructured] AS comp WHERE ISDESCENDANTNODE(comp, \"/content/alarm-controls/us/en/products\") AND [sling:resourceType] =\"" + "ems/components/structure/pdp-base-page" + "\"";
		NodeIterator itr = JCRUtils.executeSQL2Query(queryString);
		System.out.println(itr.getSize());
		
		/*
		String[] arr = {"/content/alarm-controls/us/en/products/key-switches/ka","/content/alarm-controls/us/en/products/key-switches/mck","/content/alarm-controls/us/en/products/key-switches/cy","/content/alarm-controls/us/en/products/key-switches","/content/alarm-controls/us/en/products/power/dl-1--dl-2","/content/alarm-controls/us/en/products/power/power-accessories/4152","/content/alarm-controls/us/en/products/power/power-accessories/cl-3","/content/alarm-controls/us/en/products/power/power-accessories/ac","/content/alarm-controls/us/en/products/power/power-accessories/dl2ec","/content/alarm-controls/us/en/products/power/power-accessories","/content/alarm-controls/us/en/products/power/pd4c","/content/alarm-controls/us/en/products/power/pdft","/content/alarm-controls/us/en/products/power","/content/alarm-controls/us/en/products/electric-strikes/aes-200","/content/alarm-controls/us/en/products/electric-strikes/aes-300","/content/alarm-controls/us/en/products/electric-strikes/aes-100","/content/alarm-controls/us/en/products/electric-strikes","/content/alarm-controls/us/en/products/egress-devices/ntb","/content/alarm-controls/us/en/products/egress-devices/srex-100","/content/alarm-controls/us/en/products/egress-devices/reb-series","/content/alarm-controls/us/en/products/egress-devices/nts-series","/content/alarm-controls/us/en/products/egress-devices","/content/alarm-controls/us/en/products/keypads/kp-100a","/content/alarm-controls/us/en/products/keypads/kp-400","/content/alarm-controls/us/en/products/keypads/kp-300","/content/alarm-controls/us/en/products/keypads/kp-200","/content/alarm-controls/us/en/products/keypads","/content/alarm-controls/us/en/products/monitoring-and-control-stations/ts-55","/content/alarm-controls/us/en/products/monitoring-and-control-stations/cp","/content/alarm-controls/us/en/products/monitoring-and-control-stations/monitor-and-control-station-accessories/zp","/content/alarm-controls/us/en/products/monitoring-and-control-stations/monitor-and-control-station-accessories/drc","/content/alarm-controls/us/en/products/monitoring-and-control-stations/monitor-and-control-station-accessories","/content/alarm-controls/us/en/products/monitoring-and-control-stations/ts-56","/content/alarm-controls/us/en/products/monitoring-and-control-stations/dtc","/content/alarm-controls/us/en/products/monitoring-and-control-stations/slp","/content/alarm-controls/us/en/products/monitoring-and-control-stations/monitoring-and-control-station-accessories/drc","/content/alarm-controls/us/en/products/monitoring-and-control-stations/monitoring-and-control-station-accessories/zp","/content/alarm-controls/us/en/products/monitoring-and-control-stations/monitoring-and-control-station-accessories","/content/alarm-controls/us/en/products/monitoring-and-control-stations","/content/alarm-controls/us/en/products/electromagnetic-locks/1200de","/content/alarm-controls/us/en/products/electromagnetic-locks/320s","/content/alarm-controls/us/en/products/electromagnetic-locks/1200","/content/alarm-controls/us/en/products/electromagnetic-locks/600","/content/alarm-controls/us/en/products/electromagnetic-locks/1200wp","/content/alarm-controls/us/en/products/electromagnetic-locks/600wp","/content/alarm-controls/us/en/products/electromagnetic-locks/lock-n--a-box","/content/alarm-controls/us/en/products/electromagnetic-locks/600d","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/am3335--am6335","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/drop-down-plates","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/am3310--am6310","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/filler-plates","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/dx-1","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/mounting-z-brackets","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/duc","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/am6338","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/gdh","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/l-brackets","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories/jn","/content/alarm-controls/us/en/products/electromagnetic-locks/electromagnetic-lock-accessories","/content/alarm-controls/us/en/products/electromagnetic-locks/1200d","/content/alarm-controls/us/en/products/electromagnetic-locks","/content/alarm-controls/us/en/products/push-buttons/de-1","/content/alarm-controls/us/en/products/push-buttons/ts-x-emergency","/content/alarm-controls/us/en/products/push-buttons/ts-x-vandal","/content/alarm-controls/us/en/products/push-buttons/ts-15--ts-16","/content/alarm-controls/us/en/products/push-buttons/eb-series","/content/alarm-controls/us/en/products/push-buttons/ts-x-mushroom","/content/alarm-controls/us/en/products/push-buttons/pb-series","/content/alarm-controls/us/en/products/push-buttons","/content/alarm-controls/us/en/products/remote-plates/rp--led","/content/alarm-controls/us/en/products/push-plates/pd--pn--ps-and-pt","/content/alarm-controls/us/en/products/push-plates/ft-1--ft-2","/content/alarm-controls/us/en/products/push-plates/sf","/content/alarm-controls/us/en/products/push-plates/jp-series","/content/alarm-controls/us/en/products/push-plates","/content/alarm-controls/us/en/products/system-accessories/ai-624","/content/alarm-controls/us/en/products/system-accessories/rt-1--rt-3","/content/alarm-controls/us/en/products/system-accessories/pa100","/content/alarm-controls/us/en/products/system-accessories/la32","/content/alarm-controls/us/en/products/system-accessories/ts","/content/alarm-controls/us/en/products/system-accessories/pa-200","/content/alarm-controls/us/en/products/system-accessories/mt-1","/content/alarm-controls/us/en/products/system-accessories/pa-300","/content/alarm-controls/us/en/products/system-accessories/450","/content/alarm-controls/us/en/products/system-accessories/fa-200","/content/alarm-controls/us/en/products/system-accessories/k-series","/content/alarm-controls/us/en/products/system-accessories/ld","/content/alarm-controls/us/en/products/system-accessories/ut-1","/content/alarm-controls/us/en/products/system-accessories/sc628","/content/alarm-controls/us/en/products/system-accessories","/content/alarm-controls/us/en/products/remote-plates/rp--push-button","/content/alarm-controls/us/en/products/remote-plates/mp","/content/alarm-controls/us/en/products/remote-plates/rp--plain","/content/alarm-controls/us/en/products/remote-plates","/content/alarm-controls/us/en/products/push-buttons/kr-series","/content/alarm-controls/us/en/products/push-buttons/ts-33","/content/alarm-controls/us/en/products/push-buttons/exp","/content/alarm-controls/us/en/products/push-buttons/ts-18--ts-19--ts-20","/content/alarm-controls/us/en/products/push-buttons/ts-x-square","/content/alarm-controls/us/en/products/push-buttons/gbs-1","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories/wp-4","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories/gr","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories/smb","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories/luc--lucs","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories/30583","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories/30203","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories/426","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories/wp-6","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories/10c","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories/wpc","/content/alarm-controls/us/en/products/push-buttons/push-button-accessories","/content/alarm-controls/us/en/products/push-buttons/ts-17"};
		StringBuffer sb = new StringBuffer();
		for (String str : arr) {
			String queryString = "SELECT * FROM [nt:unstructured] AS nodes WHERE ISDESCENDANTNODE (["+str+"]) AND nodes.[sling:resourceType] IS NOT NULL";
			try {
				NodeIterator itr = JCRUtils.executeSQL2Query(queryString);
				String cmpName;
				String resourceType;
				List<String> list = new ArrayList<String>();
				while (itr.hasNext()) {
					Node nextNode = itr.nextNode();
					resourceType = nextNode.getProperty("sling:resourceType").getString();
					if (!resourceType.contains("ems/components/structure") && !resourceType.contains("components/common/container")) {
						cmpName = resourceType.substring(resourceType.lastIndexOf("/")+1, resourceType.length());
						if (list.contains(cmpName)) {
							sb.append("------------------------------------   "+ str +"  ---------------------------------------");
							sb.append(System.getProperty("line.separator"));
							sb.append(resourceType);
							sb.append(System.getProperty("line.separator"));
							sb.append("----------------------------------------------------------------------------------------");
							sb.append(System.getProperty("line.separator"));
						}
						list.add(cmpName);
					}
				}			
			} catch (InvalidQueryException e) {
				e.printStackTrace();
			} catch (RepositoryException e) {
				e.printStackTrace();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		JCRUtils.closeJCRSession();

		try {
			FileWriter myWriter = new FileWriter("filename.txt");
			myWriter.write(sb.toString());
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		*/
	}

	private static void printComponentsUsedPages(String componentPath, String sitePath) {
		String queryString = "SELECT * FROM [nt:unstructured] AS comp WHERE ISDESCENDANTNODE(comp, \""+ sitePath + "\") AND [cq:template] =\"" + componentPath + "\"";
		try {
			NodeIterator itr = JCRUtils.executeSQL2Query(queryString);
			String cmpPath = "";
			Node eachNode;
			System.out.println(itr.getSize());
			while (itr.hasNext()) {
				eachNode = itr.nextNode();
				cmpPath = eachNode.getPath();
				if (!cmpPath.contains("qa-testing")) {
					System.out.println(cmpPath);
				}
			}

			JCRUtils.closeJCRSession();

		} catch (InvalidQueryException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	private static void printUsedComponents() {
		String queryString = "SELECT * FROM [nt:unstructured] AS nodes WHERE ISDESCENDANTNODE ([/content/hes-innovations/us/en]) AND nodes.[sling:resourceType] IS NOT NULL";
		try {
			NodeIterator itr = JCRUtils.executeSQL2Query(queryString);
			Set<String> uniqueComponentSet = new HashSet<String>();
			while (itr.hasNext()) {
				Node nextNode = itr.nextNode();
				uniqueComponentSet.add(nextNode.getProperty("sling:resourceType").getString());
			}

			for (String component : uniqueComponentSet) {
				System.out.println(component);
			}

			JCRUtils.closeJCRSession();

		} catch (InvalidQueryException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}

	}

	private static void printUsedTemplates() {
		//String queryString = "SELECT * FROM [cq:PageContent] AS nodes WHERE ISDESCENDANTNODE ([/content/hes-innovations/us/en]) AND nodes.[cq:template] IS NOT NULL";
		String queryString = "SELECT * FROM [cq:PageContent] AS nodes WHERE ISDESCENDANTNODE ([/content/hes-innovations/us/en]) AND nodes.[cq:template] IS NOT NULL";
		try {
			NodeIterator itr = JCRUtils.executeSQL2Query(queryString);
			Set<String> templateSet = new HashSet<String>();
			while (itr.hasNext()) {
				Node nextNode = itr.nextNode();
				templateSet.add(nextNode.getProperty("cq:template").getString());
			}
			for (String template : templateSet) {
				if (template.contains("/aeh"))
					continue;
				System.out.println(template);
			}
			JCRUtils.closeJCRSession();


		} catch (InvalidQueryException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	private static String getPagePath(String path, String env) {

		String domain = "";

		if (env.equals("local")) {
			domain = "http://localhost:4502";
		} else {
			if (path.contains("/content/assa-abloy/co/es")) {
				domain = "https://www.assaabloy.com.co";
			} else if (path.contains("/content/bruken/co/es")) {
				domain = "https://www.bruken.com.co";
			} else if (path.contains("/content/phillips/co/es")) {
				domain = "https://www.cerradurasphillips.com.co";
			} else if (path.contains("/content/yale-home/co/es")) {
				domain = "https://www.yalehome.com.co";
			} else if (path.contains("/content/assa-abloy/mx/es")) {
				domain = "https://www.assaabloy.com.mx";
			} else if (path.contains("/content/yale-home/mx/es")) {
				domain = "https://www.yalehome.com.mx";
			} else if (path.contains("/content/tesa/mx/es")) {
				domain = "https://www.tesa.com.mx";
			} else if (path.contains("/content/medeco/mx/es")) {
				domain = "https://www.medecomexico.com";
			} else if (path.contains("/content/phillips/mx/es")) {
				domain = "https://www.phillips.com.mx";
			} else if (path.contains("/content/yale/cr/es")) {
				domain = "https://www.yalehome.cr";
			} else if (path.contains("/content/yale/gt/es")) {
				domain = "https://www.yalehome.com.gt";
			} else if (path.contains("/content/bruken/gt/es")) {
				domain = "https://www.bruken.com.gt";
			} else if (path.contains("/content/yale/latam/es")) {
				domain = "https://www.yalelatinoamerica.com";
			} else if (path.contains("/content/yale/pe/es")) {
				domain = "https://www.yalehome.com.pe";
			} else if (path.contains("/content/assa-abloy/latam/es")) {
				domain = "https://www.assaabloylatinoamerica.com";
			} else if (path.contains("/content/portasespeciais/br/pt")) {
				domain = "https://www.portasespeciais.assaabloy.com.br";
			} else if (path.contains("/content/silvana/br/pt")) {
				domain = "https://www.silvana.com.br";
			} else if (path.contains("/content/papaiz/br/pt")) {
				domain = "https://www.papaiz.com.br";
			} else if (path.contains("/content/assa-abloy/br/pt")) {
				domain = "https://www.assaabloy.com.br";
			} else if (path.contains("/content/lafonte/br/pt")) {
				domain = "https://www.lafonte.com.br";
			} else if (path.contains("/content/yale/br/pt")) {
				domain = "https://www.yalehome.com.br";
			} else if (path.contains("/content/udinese/br/pt")) {
				domain = "https://www.udinese.com.br";
			} else if (path.contains("/content/yale-connect/latam/es")) {
				domain = "https://www.yaleconnecthub.com";
			}
		}

		String pagePath = domain + path.substring(0, path.indexOf("/jcr:content"));

		if (!env.equals("local")) {
			pagePath = pagePath.replace("/content/pro-tech/us", "");
			pagePath = pagePath.replace("/content/hes-innovations/us", "");
			pagePath = pagePath.replace("/content/securitron/us", "");
			pagePath = pagePath.replace("/content/assa-abloy/br/pt", "/pt-br/local/inicio");
			pagePath = pagePath.replace("/content/yale-home/co", "");
			pagePath = pagePath.replace("/content/phillips/mx/es", "/es/site/phillipscommx");
			pagePath = pagePath.replace("/content/assa-abloy/co", "");
			pagePath = pagePath.replace("/content/assa-abloy/latam/es", "/es/local/assaabloylatinoamerica");
			pagePath = pagePath.replace("/content/assa-abloy/mx", "");
			pagePath = pagePath.replace("/content/bruken/co", "");
			pagePath = pagePath.replace("/content/bruken/gt/es", "/es/site/brukencomgt");
			pagePath = pagePath.replace("/content/phillips/co", "");
			pagePath = pagePath.replace("/content/lafonte/br/pt", "/pt-br");
			pagePath = pagePath.replace("/content/medeco/mx/es", "/es/site/medecomexicocom");
			pagePath = pagePath.replace("/content/papaiz/br/pt", "/pt-br");
			pagePath = pagePath.replace("/content/portasespeciais/br/pt", "/pt-br");
			pagePath = pagePath.replace("/content/silvana/br/pt", "/pt-br/site/pagina-inicial");
			pagePath = pagePath.replace("/content/tesa/mx/es", "/es");
			pagePath = pagePath.replace("/content/udinese/br/pt", "/pt");
			pagePath = pagePath.replace("/content/yale/gt", "");
			pagePath = pagePath.replace("/content/yale/ar", "");
			pagePath = pagePath.replace("/content/yale/cr", "");
			pagePath = pagePath.replace("/content/yale/pe", "");
			pagePath = pagePath.replace("/content/yale/latam", "");
			pagePath = pagePath.replace("/content/yale-connect/latam", "");
			pagePath = pagePath.replace("/content/yale-home/mx", "");
			pagePath = pagePath.replace("/content/yale/br/pt", "/pt-br");
			pagePath = pagePath.replace("/content/bruken/mx", "");
			pagePath = pagePath.replace("/content/assa-abloy-academy/us", "");
			pagePath = pagePath.replace("/content/corbin-russwin/us", "");
			pagePath = pagePath.replace("/content/norton-rixson/us", "");
			pagePath = pagePath.replace("/content/simplek/us", "");
			pagePath = pagePath.replace("/content/assa-abloy-dss/us", "");
			pagePath = pagePath.replace("/content/secmet/us", "");
			pagePath = pagePath.replace("/content/baronmetal/us", "");
		}

		pagePath += env.equals("local")? ".html?wcmmode=disabled" : "";

		return pagePath;
	}

}
