package com.seongmin.test.aspectj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

//import org.aspectj.asm.AsmManager;
//import org.aspectj.asm.IHierarchy;
//import org.aspectj.asm.IProgramElement;
//import org.aspectj.asm.IRelationship;
//import org.aspectj.asm.IRelationshipMap;
//import org.aspectj.bridge.ISourceLocation;
//import org.aspectj.tools.ajc.Main;


/**
 * Run ajc and list files affected by advice or inter-type declarations.  
 * 
 * WARNING: Does not emit info messages for uses-pointcut dependencies.
 * @author Wes Isberg
 */
public class AffectedFilesReporter implements Runnable {

    /*
     * Walk down asm hierarchy, looking for source files,
     * and check if any of their children has a relation of
     * kind ADVICE or INTER_TYPE_DECLARATION
     */

    /**
     * Wrapper for ajc that emits list of affected files.
     * @param args the String[] of args for ajc,
     *        optionally prefixed with -to {file}
     * @throws IOException if unable to write to {file}
     */
    public static void main(String[] args) throws IOException {
//        Main main = new Main();
//        PrintStream toConfig = System.out;
//        FileOutputStream fin = null;
//        if ((args.length > 1) && ("-to".equals(args[0]))) {
//            File config = new File(args[1]);
//            fin = new FileOutputStream(config);
//            toConfig = new PrintStream(fin, true);
//            String[] temp = new String[args.length-2];
//            System.arraycopy(args, 2, temp, 0, temp.length);
//            args = temp;
//        }
//        Runnable runner = new AffectedFilesReporter(toConfig);
//        main.setCompletionRunner(runner);
//        // should add -emacssym to args if not already there
//        main.runMain(args, false);
//        if (null != fin) {
//            fin.close();
//        }        
    }
    
    final PrintStream sink;
    
    public AffectedFilesReporter(PrintStream sink) {
        this.sink = (null == sink ? System.out : sink);
    }
    
    public void run() {
//        IHierarchy hierarchy = AsmManager.getDefault().getHierarchy();
//        if (null == hierarchy) {
//            sink.println("# no structure model - use -emacssym option");
//            return;
//        }
//        List /*IProgramElement*/ nodes = new LinkedList();
//        List /*IProgramElement*/ newNodes = new LinkedList();
//        // root could be config file or blank root - either way, use kids
//        nodes.addAll(hierarchy.getRoot().getChildren());
//        while (0 < nodes.size()) {
//            for (ListIterator it = nodes.listIterator(); it.hasNext();) {
//                IProgramElement node = (IProgramElement) it.next();
//                if (node.getKind().isSourceFileKind()) {
//                    if (isAffected(node)) {
//                        ISourceLocation loc = node.getSourceLocation();
//                        sink.println(loc.getSourceFile().getPath());
//                    }
//                } else {
//                    // XXX uncertain of structure - traverse all??
//                    newNodes.addAll(node.getChildren());
//                }
//                it.remove();
//            }
//            nodes.addAll(newNodes);
//            newNodes.clear();
//        }
    }

    /**
     * Return true if this file node is affected by any aspects.
     * Recursively search children for any effect,
     * and halt on first affect found.
     * @param node the IProgramElementNode for a source file
     * @return true if affected.
     */
//    private boolean isAffected(final IProgramElement fileNode) {
//        final IRelationshipMap map  = 
//            AsmManager.getDefault().getRelationshipMap();     
//        List /*IProgramElement*/ nodes = new LinkedList();
//        List /*IProgramElement*/ newNodes = new LinkedList();
//        nodes.add(fileNode);
//        while (0 < nodes.size()) {
//            for (ListIterator iter = nodes.listIterator(); 
//                 iter.hasNext();) {
//                IProgramElement node = (IProgramElement) iter.next();
//                List relations = map.get(node);
//                if (null != relations) {
//                    for (Iterator riter = relations.iterator(); 
//                        riter.hasNext();) {
//                        IRelationship.Kind kind =
//                        ((IRelationship) riter.next()).getKind();
//                        if ((kind == IRelationship.Kind.ADVICE)
//                            || (kind == IRelationship.Kind.DECLARE_INTER_TYPE)) {
//                            return true;
//                        }
//                    }
//                }
//                iter.remove();
//                newNodes.addAll(node.getChildren());
//            }
//            nodes.addAll(newNodes);
//            newNodes.clear();
//        }
//        return false;
//    }
}