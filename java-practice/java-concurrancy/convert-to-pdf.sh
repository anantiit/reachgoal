#!/bin/bash
# Script to convert STUDY_GUIDE.md to PDF with nice formatting

echo "Converting STUDY_GUIDE.md to PDF..."
echo ""

# Method 1: Using Chrome/Safari to print to PDF (RECOMMENDED)
cat << 'EOF'
=== METHOD 1: Print to PDF from Browser (EASIEST) ===

1. Open the generated STUDY_GUIDE.html in your browser:
   open STUDY_GUIDE.html

2. Press Cmd+P (Print)

3. Select "Save as PDF" as the destination

4. Adjust settings:
   - Paper size: A4 or Letter
   - Margins: Default or Minimum
   - Scale: 100% or "Fit to page"
   - Background graphics: Enabled (for syntax highlighting)

5. Save as STUDY_GUIDE.pdf

This gives you the BEST formatting and navigation!

EOF

# Method 2: Using pandoc with chromium-based rendering
cat << 'EOF'
=== METHOD 2: Command-line with Chromium (if you have Chrome) ===

If you have Google Chrome or Chromium installed:

/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome \
  --headless --disable-gpu --print-to-pdf=STUDY_GUIDE.pdf \
  --print-to-pdf-no-header \
  file://$(pwd)/STUDY_GUIDE.html

Or with Safari:
# Safari doesn't support headless PDF generation easily

EOF

# Method 3: Install weasyprint for command-line conversion
cat << 'EOF'
=== METHOD 3: Install PDF tools ===

Option A - Install weasyprint (Python-based):
  brew install python3
  pip3 install weasyprint
  weasyprint STUDY_GUIDE.html STUDY_GUIDE.pdf

Option B - Install prince (commercial but free for personal use):
  Download from: https://www.princexml.com/download/
  prince STUDY_GUIDE.html -o STUDY_GUIDE.pdf

Option C - Use online converter:
  1. Open https://cloudconvert.com/md-to-pdf
  2. Upload STUDY_GUIDE.md
  3. Download PDF

EOF

echo "HTML version created: STUDY_GUIDE.html"
echo ""
echo "RECOMMENDED: Use Method 1 (Print from browser) for best results!"
echo ""
