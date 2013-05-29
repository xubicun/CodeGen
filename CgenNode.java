/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file

import java.io.PrintStream;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Set;
import java.util.HashSet;

class MethodPair {

    public final CgenNode cnode;
    public final method met;

    public MethodPair(CgenNode klass, method met){
        cnode = klass;
        this.met = met;
    }

    //methodpairs are "equal" if their methods have the same name (used for overriding)
    public boolean equals(Object o){
        return o instanceof MethodPair && met.name == ((MethodPair) o).met.name;
    }
}

class CgenNode extends class_ {
    /** The parent of this node in the inheritance tree */
    private CgenNode parent;

    /** The children of this node in the inheritance tree */
    private Vector<CgenNode> children;

    /** Indicates a basic class */
    final static int Basic = 0;

    /** Indicates a class that came from a Cool program */
    final static int NotBasic = 1;
    
    /** Does this node correspond to a basic class? */
    private int basic_status;



    //class tag
    private int tag;

    /** Vector of the class' methods, including inherited ones **/
    private Vector<MethodPair> methods;

    /** Vector of the class' attributes, including inherited ones **/
    private Vector<attr> attrs;

    /** Constructs a new CgenNode to represent class "c".
     * @param c the class
     * @param basic_status is this class basic or not
     * @param table the class table
     * */
    CgenNode(AbstractClass c, int basic_status, CgenClassTable table) {
	super(0, c.getName(), c.getParent(), c.getFeatures(), c.getFilename());
	this.parent = null;
	this.children = new Vector<CgenNode>();
	this.basic_status = basic_status;
    this.tag = -1;
	AbstractTable.stringtable.addString(name.getString());
    }

    void addChild(CgenNode child) {
	children.addElement(child);
    }

    /** Gets the children of this class
     * @return the children
     * */
    Enumeration getChildren() {
	   return children.elements(); 
    }

    /** Sets the parent of this class.
     * @param parent the parent
     * */
    void setParentNd(CgenNode parent) {
	if (this.parent != null) {
	    Utilities.fatalError("parent already set in CgenNode.setParent()");
	}
	if (parent == null) {
	    Utilities.fatalError("null parent in CgenNode.setParent()");
	}
	this.parent = parent;
    }    
	

    /** Gets the parent of this class
     * @return the parent
     * */
    CgenNode getParentNd() {
	return parent; 
    }

    /** Returns true is this is a basic class.
     * @return true or false
     * */
    boolean basic() { 
	return basic_status == Basic; 
    }

    void setClassTag(int tag){
        if(this.tag != -1){
            Utilities.fatalError("class tag already set to " + this.tag + " in CgenNode.setClassTag");
        }
        this.tag = tag;
    }

    int getClassTag(){
        if(tag == -1){
            Utilities.fatalError("class tag not yet set in CgenNode.getClassTag");
        }
        return tag;
    }

    void setMethods(Vector<MethodPair> methods){
        if(this.methods != null){
            Utilities.fatalError("methods list already set in CgenNode.setMethods");
        }
        this.methods = methods;
    }

    //returns a list of class,method pairs with overridden methods filtered out.
    Vector<MethodPair> getMethods(){
        if(methods == null){
            Utilities.fatalError("methods list not yet set in CgenNode.getMethods");
        }
        return methods;
    }

    void setAttrs(Vector<attr> attrs){
        if(this.attrs != null){
            Utilities.fatalError("attrs list already set in CgenNode.setAttrs");
        }
        this.attrs = attrs;
    }

    //no filtering since attrs cannot be overridden
    Vector<attr> getAttrs(){
        if(attrs == null){
            Utilities.fatalError("attrs list not yet set in CgenNode.getAttrs");
        }
        return attrs;
    }

    int getMethodOffset(AbstractSymbol methodName){
        for(int i = 0; i < methods.size(); i++){
            if(methods.get(i).met.name == methodName) return CgenSupport.WORD_SIZE * i; 
        }
        return -1;
    }

}
    

    
