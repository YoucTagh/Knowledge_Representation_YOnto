/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KR.Models;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author YoucTagh
 */
public class SparqlModel {

    public SparqlModel() {
    }

    public String requete(String fullTxt, String sparqlQuery) {

        String file = "file:tmp.rdf"; // le chemain sur votre machine
        String NL = System.getProperty("line.separator");
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

        createTmpFile(fullTxt);
        m.read(file, "RDF/XML");
        //definition des préfixes
        String prefix0 = "PREFIX foaf:<http://xmlns.com/foaf/0.1/>";
        String prefix = "PREFIX Ns: <http://www.u-picardie.fr/~furst/human.rdfs#>";
        String prefix1 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
        String prefix2 = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
        String prefixG = prefix + NL + prefix2 + NL + prefix1 + NL + prefix0 + NL;

        // **** requete ****
        String queryString = prefixG + sparqlQuery;

        /* Exécution des requêtes */
        Query query;
        QueryExecution qexec = null;

        String feedBack = "Resultat du requete : \n";
        try {
            query = QueryFactory.create(queryString, Syntax.syntaxARQ);
            qexec = QueryExecutionFactory.create(query, m);
            // Assumption: it’s a SELECT query.

            ResultSet rs = qexec.execSelect();
            //ResultSetFormatter.out(rs, query);

            feedBack += ResultSetFormatter.asText(rs, query);
            System.out.println(feedBack);
            qexec.close();
        } catch (Exception e) {
            feedBack += "Bad Query : Ya akhi rak ghalet f Syntax";
        } finally {
            // QueryExecution objects should be closed to free any system resources

            File filee = new File("tmp.rdf");
            if (filee.exists()) {
                filee.delete();
            }
        }

        return feedBack;

    }

    public void createTmpFile(String content) {
        File file = new File("tmp.rdf");

        try (FileOutputStream fop = new FileOutputStream(file)) {

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
