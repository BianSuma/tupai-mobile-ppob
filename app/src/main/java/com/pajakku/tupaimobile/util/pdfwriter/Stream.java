//
//  Android PDF Writer
//  http://coderesearchlabs.com/androidpdfwriter
//
//  by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com)
//

package com.pajakku.tupaimobile.util.pdfwriter;

public class Stream extends EnclosedContent {

    public Stream() {
        super("stream\n", "endstream\n");
    }

}
