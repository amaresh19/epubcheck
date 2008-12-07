/*
 * Copyright (c) 2007 Adobe Systems Incorporated
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of
 *  this software and associated documentation files (the "Software"), to deal in
 *  the Software without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *  the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.adobe.epubcheck.util;

import java.io.PrintWriter;

import com.adobe.epubcheck.api.Report;

public class WriterReportImpl implements Report {

	private PrintWriter out;
	
	public WriterReportImpl( PrintWriter out ) {
		this.out = out;
	}
	
	public void error(String resource, int line, String message) {
		out.println("ERROR: " + (resource == null ? "[top level]" : resource) +
				(line <= 0 ? "" : "(" + line + ")") + ": " + message );
	}
	public void warning(String resource, int line, String message) {
		out.println("WARNING: " + (resource == null ? "[top level]" : resource) +
				(line <= 0 ? "" : "(" + line + ")") + message );
	}

}
