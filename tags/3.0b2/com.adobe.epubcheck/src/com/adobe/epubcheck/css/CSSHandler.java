/*
 * Copyright (c) 2011 Adobe Systems Incorporated
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

package com.adobe.epubcheck.css;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.css.sac.SACMediaList;
import org.w3c.css.sac.SelectorList;

import com.adobe.epubcheck.api.Report;
import com.adobe.epubcheck.opf.OPFChecker30;
import com.adobe.epubcheck.opf.XRefChecker;
import com.adobe.epubcheck.util.EPUBVersion;
import com.adobe.epubcheck.util.Messages;
import com.adobe.epubcheck.util.PathUtil;

class CSSHandler implements DocumentHandler {

	String path;

	XRefChecker xrefChecker;

	Report report;

	boolean fontFace = false;

	EPUBVersion version;

	public void comment(String text) throws CSSException {
	}

	public void endDocument(InputSource source) throws CSSException {
	}

	public void endFontFace() throws CSSException {
		fontFace = false;
	}

	public void endMedia(SACMediaList media) throws CSSException {
	}

	public void endPage(String name, String pseudo_page) throws CSSException {
	}

	public void endSelector(SelectorList selectors) throws CSSException {
	}

	public void ignorableAtRule(String atRule) throws CSSException {
	}

	public void importStyle(String uri, SACMediaList media,
			String defaultNamespaceURI) throws CSSException {
	}

	public void namespaceDeclaration(String prefix, String uri)
			throws CSSException {
	}

	public CSSHandler(String path, XRefChecker xrefChecker, Report report,
			EPUBVersion version) {
		this.path = path;
		this.xrefChecker = xrefChecker;
		this.report = report;
		this.version = version;
	}

	public void property(String name, LexicalUnit value, boolean arg2)
			throws CSSException {

		if (name != null && name.equals("src"))
			if (value != null
					&& value.getLexicalUnitType() == LexicalUnit.SAC_URI)
				if (value.getStringValue() != null) {

					String uri = value.getStringValue();

					uri = PathUtil.resolveRelativeReference(path, uri);

					xrefChecker.registerReference(path, -1, -1, uri,
							XRefChecker.RT_GENERIC);

					if (fontFace && version == EPUBVersion.VERSION_3) {
						String fontMimeType = xrefChecker.getMimeType(uri);
						if (!OPFChecker30.isBlessedFontType(fontMimeType))
							report.error(path, -1, -1, "Font-face reference "
									+ uri + "to non-standard font type "
									+ fontMimeType);
					}

				} else
					report.error(path, -1, -1, Messages.NULL_REF);
	}

	public void startDocument(InputSource source) throws CSSException {
	}

	public void startFontFace() throws CSSException {
		fontFace = true;
	}

	public void startMedia(SACMediaList media) throws CSSException {
	}

	public void startPage(String name, String pseudo_page) throws CSSException {
	}

	public void startSelector(SelectorList selectors) throws CSSException {
	}

}