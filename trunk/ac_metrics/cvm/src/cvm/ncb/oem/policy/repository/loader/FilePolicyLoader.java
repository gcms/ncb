/**
 * @author Yali Wu
 * @since November 19 2008
 *
 * This class looks up the policy from the repository, and 
 * loads the policy in XML format to an object representation
 */


package cvm.ncb.oem.policy.repository.loader;

import cvm.sb.policy.metadata.Attribute;
import cvm.sb.policy.metadata.Feature;
import cvm.sb.policy.repository.*;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class FilePolicyLoader {

    private final static String scopeTagLabel = "scope";
    private final static String conditionTagLabel = "condition";
    private final static String bvalueTagLabel = "businessValue";
    private final static String decisionTagLabel = "decision";


    private final static String serviceTagLabel = "service";
    private final static String operationSTagLabel = "operation";
    private final static String activeTagLable = "active";

    private final static String featureTagLabel = "feature";
    private final static String operationCTagLabel = "operation";
    private final static String literalTagLabel = "literal";

    private final static String bgroupTagLabel = "businessGroup";
    private final static String bvalTagLabel = "value";

    private final static String paramTagLabel = "param";
    private final static String operationDTagLabel = "operation";
    private final static String valueTagLabel = "value";

    private final static String policyTypeTagLable = "policyType";
    private final static String policyNameTagLable = "policyName";

    private File rootDir;
    private File featureTree;

    FilePolicyLoader(File rootPath, String featureTreeName) {
        rootDir = rootPath;
        featureTree = new File(rootDir, featureTreeName);
    }

    public static FilePolicyLoader createInstance(URI path) {
        return new FilePolicyLoader(new File(path), "featureTree.xml");
    }

    public Policy load(File policyFile) {
        Document policyDoc = getPolicyDocument(policyFile);
        Node scopeNode = policyDoc.getElementsByTagName(scopeTagLabel).item(0);
        Node conditionNode = policyDoc.getElementsByTagName(conditionTagLabel).item(0);
        Node decisionNode = policyDoc.getElementsByTagName(decisionTagLabel).item(0);
        Node bvalueNode = policyDoc.getElementsByTagName(bvalueTagLabel).item(0);

        Node serviceNode = ((Element) scopeNode).getElementsByTagName(serviceTagLabel).item(0);
        Node operationSNode = ((Element) scopeNode).getElementsByTagName(operationSTagLabel).item(0);
        Node activeNode = ((Element) scopeNode).getElementsByTagName(activeTagLable).item(0);
        Scope scope = new Scope(serviceNode.getTextContent(), operationSNode.getTextContent(), activeNode.getTextContent());

        Node featureNode = ((Element) conditionNode).getElementsByTagName(featureTagLabel).item(0);
        Node operationCNode = ((Element) conditionNode).getElementsByTagName(operationCTagLabel).item(0);
        Node literalNode = ((Element) conditionNode).getElementsByTagName(literalTagLabel).item(0);

        Condition condition = new Condition(featureNode.getTextContent(), operationCNode.getTextContent(), literalNode.getTextContent());


        Node businessGroupNode = ((Element) bvalueNode).getElementsByTagName(bgroupTagLabel).item(0);
        Node bvalNode = ((Element) bvalueNode).getElementsByTagName(bvalTagLabel).item(0);
        BusinessValue bvalue = new BusinessValue(businessGroupNode.getTextContent(), bvalNode.getTextContent());

        Node paramNode = ((Element) decisionNode).getElementsByTagName(paramTagLabel).item(0);
        Node operationDNode = ((Element) decisionNode).getElementsByTagName(operationDTagLabel).item(0);
        Node valueNode = ((Element) decisionNode).getElementsByTagName(valueTagLabel).item(0);
        Decision decision = new Decision(paramNode.getTextContent(), operationDNode.getTextContent(), valueNode.getTextContent());


        String theType = policyDoc.getDocumentElement().getAttributes().getNamedItem(policyTypeTagLable).getNodeValue();
        String theName = policyDoc.getDocumentElement().getAttributes().getNamedItem(policyNameTagLable).getNodeValue();

        return new Policy(scope, condition, bvalue, decision, theType, theName);
    }

    public boolean isPolicyFile(String filename) {
        return filename.toLowerCase().endsWith("xml") && !filename.equalsIgnoreCase(featureTree.getName());
    }


    private List<Policy> getAllPolicies() {
        List<Policy> policies = new ArrayList<Policy>();

        for (File file : rootDir.listFiles()) {
            if (isPolicyFile(file.getName())) {
                policies.add(load(file));
            }
        }

        return policies;
    }

    public List<Policy> lookup(String feature, String operation) {
        List<Policy> arr = new ArrayList<Policy>();

        for (Policy policy : getAllPolicies()) {
            if (policy.getCondition().getFeature().equals(feature)
                    && policy.getCondition().getOperation().equals(operation))
                arr.add(policy);
        }
        return arr;
    }


    private Document getPolicyDocument(File policyFile) {
        FileInputStream pFileIn = null;
        try {
            pFileIn = new FileInputStream(policyFile);
            InputSource pInputSource = new InputSource(pFileIn);
            DOMParser policyParser = new DOMParser();
            policyParser.parse(pInputSource);
            return policyParser.getDocument();
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if (pFileIn != null)
                try { pFileIn.close(); } catch (IOException ignored) { }
        }

        // TODO: notify error
        return null;
    }

    public Feature loadFeatureTree() {
        Document treeDoc = getPolicyDocument(featureTree);
        return loadFeatures(treeDoc.getDocumentElement(), null);
    }

    /*
      * Load the features from the document recursively
      */
    private Feature loadFeatures(Element ele, Feature parent) {
        Feature aFeature = new Feature(ele.getNodeName(), parent);

        for (int i = 0; i < ele.getAttributes().getLength(); i++) {
            Node item = ele.getAttributes().item(i);
            Attribute attr = new Attribute(item.getNodeName(), item.getNodeValue());
            aFeature.addAttribute(attr);
        }

        if (ele.getChildNodes().getLength() > 0) {
            for (int i = 0; i < ele.getChildNodes().getLength(); i++) {
                Node item = ele.getChildNodes().item(i);
                if (item.getNodeType() != Node.TEXT_NODE)
                    aFeature.addSubFeature(loadFeatures((Element) item, aFeature));
            }
        }
        return aFeature;
    }
}
		

