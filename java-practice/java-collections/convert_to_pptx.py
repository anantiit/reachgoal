#!/usr/bin/env python3
"""
Convert Java Collections & Streams HTML presentation to PPTX format.
Preserves styling, layout, and content for Google Slides compatibility.
"""

from pptx import Presentation
from pptx.util import Inches, Pt
from pptx.enum.text import PP_ALIGN, MSO_ANCHOR
from pptx.dml.color import RGBColor
from pptx.enum.shapes import MSO_SHAPE

# Color scheme from HTML
COLOR_BLUE_DARK = RGBColor(15, 23, 42)      # #0F172A
COLOR_BLUE_PRIMARY = RGBColor(30, 58, 138)  # #1E3A8A
COLOR_BLUE_ACCENT = RGBColor(59, 130, 246)  # #3B82F6
COLOR_ORANGE_PRIMARY = RGBColor(234, 88, 12)  # #EA580C
COLOR_ORANGE_LIGHT = RGBColor(255, 237, 213)  # #FFEDD5
COLOR_SLATE_50 = RGBColor(248, 250, 252)
COLOR_SLATE_200 = RGBColor(226, 232, 240)
COLOR_SLATE_700 = RGBColor(51, 65, 85)
COLOR_GREEN_50 = RGBColor(240, 253, 244)
COLOR_GREEN_500 = RGBColor(34, 197, 94)
COLOR_GREEN_800 = RGBColor(22, 101, 52)
COLOR_RED_50 = RGBColor(254, 242, 242)
COLOR_RED_500 = RGBColor(239, 68, 68)
COLOR_RED_700 = RGBColor(185, 28, 28)
COLOR_WHITE = RGBColor(255, 255, 255)
COLOR_CODE_BG = RGBColor(241, 245, 249)

def create_presentation():
    """Create and configure the presentation."""
    prs = Presentation()
    prs.slide_width = Inches(10)
    prs.slide_height = Inches(5.625)  # 16:9 aspect ratio
    return prs

def add_title_slide(prs):
    """Slide 1: Title slide with gradient effect."""
    slide = prs.slides.add_slide(prs.slide_layouts[6])  # Blank layout

    # Add decorative rings (background circles)
    ring1 = slide.shapes.add_shape(
        MSO_SHAPE.OVAL, Inches(7), Inches(-1), Inches(4), Inches(4)
    )
    ring1.fill.background()
    ring1.line.color.rgb = COLOR_BLUE_ACCENT
    ring1.line.width = Pt(2)

    ring2 = slide.shapes.add_shape(
        MSO_SHAPE.OVAL, Inches(-0.5), Inches(3), Inches(3), Inches(3)
    )
    ring2.fill.background()
    ring2.line.color.rgb = COLOR_ORANGE_PRIMARY
    ring2.line.width = Pt(8)

    # Title text box
    title_box = slide.shapes.add_textbox(Inches(1), Inches(1.5), Inches(8), Inches(2))
    tf = title_box.text_frame
    tf.word_wrap = True

    # Header label
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "🗂️ PROFESSIONAL DEEP DIVE"
    run.font.size = Pt(14)
    run.font.bold = True
    run.font.color.rgb = COLOR_ORANGE_PRIMARY

    # Main title
    p = tf.add_paragraph()
    run = p.add_run()
    run.text = "Java Collections & Streams:\nArchitecture to Performance"
    run.font.size = Pt(48)
    run.font.bold = True
    run.font.color.rgb = COLOR_BLUE_PRIMARY

    # Metadata box
    meta_box = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(1), Inches(3.5), Inches(8), Inches(0.8)
    )
    meta_box.fill.solid()
    meta_box.fill.fore_color.rgb = COLOR_SLATE_50
    meta_box.line.color.rgb = COLOR_ORANGE_PRIMARY
    meta_box.line.width = Pt(3)

    tf = meta_box.text_frame
    tf.word_wrap = True
    p = tf.paragraphs[0]
    p.text = "Target Audience: Principal Engineers & Seniors  |  Environment: Java 17+  |  Duration: 60 Minutes"
    p.font.size = Pt(12)
    p.font.bold = True
    p.font.color.rgb = COLOR_BLUE_PRIMARY
    p.alignment = PP_ALIGN.CENTER

    # Topics outline
    topics_box = slide.shapes.add_textbox(Inches(1), Inches(4.5), Inches(8), Inches(1))
    tf = topics_box.text_frame
    tf.word_wrap = True
    p = tf.paragraphs[0]
    p.text = "• Framework Architecture  • List/Set/Map Implementations  • Stream API Fundamentals  • Advanced Streams  • Parallel Streams & Performance"
    p.font.size = Pt(11)
    p.font.color.rgb = COLOR_SLATE_700
    p.alignment = PP_ALIGN.CENTER

def add_header(slide, title, slide_number):
    """Add consistent header to slides."""
    # Title
    title_box = slide.shapes.add_textbox(Inches(0.5), Inches(0.3), Inches(7.5), Inches(0.5))
    tf = title_box.text_frame
    p = tf.paragraphs[0]
    p.text = title
    p.font.size = Pt(32)
    p.font.bold = True
    p.font.color.rgb = COLOR_BLUE_PRIMARY

    # Slide number
    num_box = slide.shapes.add_textbox(Inches(8.5), Inches(0.3), Inches(1), Inches(0.5))
    tf = num_box.text_frame
    p = tf.paragraphs[0]
    p.text = f"SLIDE {slide_number:02d}"
    p.font.size = Pt(12)
    p.font.bold = True
    p.font.color.rgb = COLOR_ORANGE_PRIMARY
    p.alignment = PP_ALIGN.RIGHT

    # Orange underline
    line = slide.shapes.add_shape(
        MSO_SHAPE.RECTANGLE, Inches(0.5), Inches(0.85), Inches(9), Inches(0.05)
    )
    line.fill.solid()
    line.fill.fore_color.rgb = COLOR_ORANGE_PRIMARY
    line.line.fill.background()

def add_card(slide, left, top, width, height, title, content, bg_color=COLOR_SLATE_50, border_color=COLOR_BLUE_ACCENT):
    """Add a styled card with title and content."""
    card = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, left, top, width, height
    )
    card.fill.solid()
    card.fill.fore_color.rgb = bg_color
    card.line.color.rgb = border_color
    card.line.width = Pt(3)

    tf = card.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_right = Inches(0.15)
    tf.margin_top = Inches(0.1)
    tf.margin_bottom = Inches(0.1)

    # Title
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = title + "\n"
    run.font.size = Pt(14)
    run.font.bold = True
    run.font.color.rgb = COLOR_BLUE_PRIMARY if bg_color == COLOR_SLATE_50 else COLOR_GREEN_800 if bg_color == COLOR_GREEN_50 else COLOR_RED_700

    # Content
    if isinstance(content, list):
        for item in content:
            p = tf.add_paragraph()
            p.text = item
            p.font.size = Pt(10)
            p.font.color.rgb = COLOR_SLATE_700
            p.level = 0
    else:
        p = tf.add_paragraph()
        p.text = content
        p.font.size = Pt(10)
        p.font.color.rgb = COLOR_SLATE_700

def add_code_block(slide, left, top, width, height, code):
    """Add a code block with monospace font and background."""
    code_box = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, left, top, width, height
    )
    code_box.fill.solid()
    code_box.fill.fore_color.rgb = COLOR_CODE_BG
    code_box.line.color.rgb = COLOR_SLATE_200
    code_box.line.width = Pt(1)

    tf = code_box.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_right = Inches(0.15)
    tf.margin_top = Inches(0.15)
    tf.margin_bottom = Inches(0.15)

    p = tf.paragraphs[0]
    p.text = code
    p.font.name = 'Courier New'
    p.font.size = Pt(9)
    p.font.color.rgb = COLOR_BLUE_DARK
    p.alignment = PP_ALIGN.LEFT

def add_slide_02(prs):
    """Slide 2: Why Learn Collections & Streams?"""
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Why Learn Collections & Streams?", 2)

    # Left column cards
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(1.5),
             "💼 Industry Reality",
             ["• 95% of Java code uses Collections",
              "• Avg enterprise app: 60-80% data manipulation",
              "• 70% of bottlenecks from poor choices",
              "• #1 commented topic in code reviews"])

    add_card(slide, Inches(0.5), Inches(2.8), Inches(4.5), Inches(1.3),
             "💰 Business Value",
             ["• Performance: Right collection = 100-1000x speedup",
              "• Maintainability: Stream code is 40% more readable",
              "• Cost: Microservice inefficiency = $1000s in cloud"],
             COLOR_ORANGE_LIGHT, COLOR_ORANGE_PRIMARY)

    # Right column
    career_box = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(5.2), Inches(1.2), Inches(4.3), Inches(1.5)
    )
    career_box.fill.solid()
    career_box.fill.fore_color.rgb = COLOR_BLUE_PRIMARY
    career_box.line.fill.background()

    tf = career_box.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_top = Inches(0.1)

    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "🚀 Career Impact (Senior → Principal)\n"
    run.font.size = Pt(14)
    run.font.bold = True
    run.font.color.rgb = COLOR_ORANGE_PRIMARY

    for item in ["✓ Deep understanding of Big-O characteristics",
                 "✓ Ability to scale from 10k to 10M records",
                 "✓ Expertise in concurrent collection patterns"]:
        p = tf.add_paragraph()
        p.text = item
        p.font.size = Pt(10)
        p.font.color.rgb = COLOR_WHITE

    add_card(slide, Inches(5.2), Inches(2.8), Inches(4.3), Inches(1.3),
             "Concrete Examples Today:",
             ["• ArrayList saving 2s on 100k ops vs LinkedList",
              "• LRU Cache with LinkedHashMap (Production Pattern)",
              "• ConcurrentHashMap handling 1M concurrent requests"])

def add_slide_03(prs):
    """Slide 3: Collections Framework Architecture."""
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Collections Framework - Architecture", 3)

    # Background
    bg = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(1.5), Inches(1.3), Inches(7), Inches(3.5)
    )
    bg.fill.solid()
    bg.fill.fore_color.rgb = COLOR_SLATE_50
    bg.line.color.rgb = COLOR_SLATE_200
    bg.line.width = Pt(1)

    # Collection interface at top
    coll = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(4), Inches(1.6), Inches(2), Inches(0.4)
    )
    coll.fill.solid()
    coll.fill.fore_color.rgb = COLOR_ORANGE_LIGHT
    coll.line.color.rgb = COLOR_ORANGE_PRIMARY
    coll.line.width = Pt(2)
    tf = coll.text_frame
    p = tf.paragraphs[0]
    p.text = "Collection <E>"
    p.font.size = Pt(12)
    p.font.bold = True
    p.font.color.rgb = COLOR_ORANGE_PRIMARY
    p.alignment = PP_ALIGN.CENTER

    # Sub-interfaces: List, Set, Queue
    interfaces = [
        ("List <E>", Inches(2), ["ArrayList", "LinkedList"]),
        ("Set <E>", Inches(4.5), ["HashSet", "TreeSet"]),
        ("Queue <E>", Inches(7), ["PriorityQueue", "ArrayDeque"])
    ]

    for name, left, impls in interfaces:
        # Interface box
        iface = slide.shapes.add_shape(
            MSO_SHAPE.ROUNDED_RECTANGLE, left, Inches(2.5), Inches(1.3), Inches(0.35)
        )
        iface.fill.solid()
        iface.fill.fore_color.rgb = COLOR_ORANGE_LIGHT
        iface.line.color.rgb = COLOR_ORANGE_PRIMARY
        iface.line.width = Pt(2)
        tf = iface.text_frame
        p = tf.paragraphs[0]
        p.text = name
        p.font.size = Pt(11)
        p.font.bold = True
        p.font.color.rgb = COLOR_ORANGE_PRIMARY
        p.alignment = PP_ALIGN.CENTER

        # Implementation boxes
        for i, impl in enumerate(impls):
            impl_box = slide.shapes.add_shape(
                MSO_SHAPE.ROUNDED_RECTANGLE, left, Inches(3.1 + i * 0.4), Inches(1.3), Inches(0.3)
            )
            impl_box.fill.solid()
            impl_box.fill.fore_color.rgb = COLOR_WHITE
            impl_box.line.color.rgb = COLOR_BLUE_PRIMARY
            impl_box.line.width = Pt(2)
            tf = impl_box.text_frame
            p = tf.paragraphs[0]
            p.text = impl
            p.font.size = Pt(9)
            p.font.bold = True
            p.font.color.rgb = COLOR_BLUE_PRIMARY
            p.alignment = PP_ALIGN.CENTER

    # Separator line and Map interface
    sep = slide.shapes.add_shape(
        MSO_SHAPE.RECTANGLE, Inches(2), Inches(4.1), Inches(6), Inches(0.02)
    )
    sep.fill.solid()
    sep.fill.fore_color.rgb = COLOR_SLATE_200
    sep.line.fill.background()

    # Map interface
    map_iface = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(4), Inches(4.3), Inches(2), Inches(0.35)
    )
    map_iface.fill.solid()
    map_iface.fill.fore_color.rgb = COLOR_ORANGE_LIGHT
    map_iface.line.color.rgb = COLOR_ORANGE_PRIMARY
    map_iface.line.width = Pt(2)
    tf = map_iface.text_frame
    p = tf.paragraphs[0]
    p.text = "Map <K, V>"
    p.font.size = Pt(11)
    p.font.bold = True
    p.font.color.rgb = COLOR_ORANGE_PRIMARY
    p.alignment = PP_ALIGN.CENTER

    # Map implementations
    for i, impl in enumerate(["HashMap", "ConcurrentHashMap"]):
        impl_box = slide.shapes.add_shape(
            MSO_SHAPE.ROUNDED_RECTANGLE, Inches(3 + i * 1.5), Inches(4.8), Inches(1.4), Inches(0.3)
        )
        impl_box.fill.solid()
        impl_box.fill.fore_color.rgb = COLOR_WHITE
        impl_box.line.color.rgb = COLOR_BLUE_PRIMARY
        impl_box.line.width = Pt(2)
        tf = impl_box.text_frame
        p = tf.paragraphs[0]
        p.text = impl
        p.font.size = Pt(9)
        p.font.bold = True
        p.font.color.rgb = COLOR_BLUE_PRIMARY
        p.alignment = PP_ALIGN.CENTER

def add_slide_04(prs):
    """Slide 4: ArrayList - What/Why/When."""
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "ArrayList - What/Why/When", 4)

    # Left column
    add_card(slide, Inches(0.5), Inches(1.2), Inches(5.5), Inches(1.2),
             "📌 What is ArrayList?",
             "Resizable array implementation. Default capacity 16, grows by 1.5x. Contiguous memory storage. Random access: O(1). Insert middle: O(n).")

    # Pros/Cons cards
    add_card(slide, Inches(0.5), Inches(2.5), Inches(2.6), Inches(1.6),
             "💡 Why / When to Use",
             ["✅ Lightning-fast random access",
              "✅ Memory efficient (~4 bytes overhead)",
              "✅ Best cache locality",
              "✅ Reading > modifying",
              "✅ Appending to end"],
             COLOR_GREEN_50, COLOR_GREEN_500)

    add_card(slide, Inches(3.4), Inches(2.5), Inches(2.6), Inches(1.6),
             "❌ When NOT to Use",
             ["❌ Frequent insertions at beginning",
              "❌ Middle insertions common",
              "❌ Need thread-safety"],
             COLOR_RED_50, COLOR_RED_500)

    # Code example
    code = """// ✅ BEST PRACTICE: Fast random access & append
List<String> names = new ArrayList<>();
names.add("Alice");        // O(1) amortized
String first = names.get(0); // O(1) direct access

// ✅ Pre-size if known capacity
List<User> users = new ArrayList<>(1000);

// ❌ ANTI-PATTERN: Inserting at index 0
// Forces entire array (O(n)) to shift right!
for (int i = 0; i < 1000; i++) {
    names.add(0, "Item");
}

// ✅ Use ArrayDeque for Queue needs
Deque<String> queue = new ArrayDeque<>();
queue.addFirst("Item"); // O(1) constant time"""

    add_code_block(slide, Inches(6.2), Inches(1.2), Inches(3.3), Inches(2.9), code)

def add_slide_05(prs):
    """Slide 5: LinkedList - What/Why/When."""
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "LinkedList - What/Why/When", 5)

    # Left column
    add_card(slide, Inches(0.5), Inches(1.2), Inches(5.5), Inches(1.2),
             "📌 What is LinkedList?",
             "Doubly-linked list (prev/data/next nodes). Implements List AND Deque. Grows node-by-node. Random access: O(n). Ends operations: O(1). Memory: ~40 bytes/element (10x ArrayList!).")

    # Pros/Cons cards
    add_card(slide, Inches(0.5), Inches(2.5), Inches(2.6), Inches(1.6),
             "💡 Why / When to Use",
             ["✅ O(1) ops at both ends (Deque)",
              "✅ No array resizing overhead",
              "✅ Efficient iterator insertions",
              "✅ Queue implementations"],
             COLOR_GREEN_50, COLOR_GREEN_500)

    add_card(slide, Inches(3.4), Inches(2.5), Inches(2.6), Inches(1.6),
             "❌ When NOT to Use",
             ["❌ Random access needed",
              "❌ Memory constrained",
              "❌ General-purpose List"],
             COLOR_RED_50, COLOR_RED_500)

    # Code example
    code = """// ✅ BEST PRACTICE: Using as strict Deque
Deque<String> queue = new LinkedList<>();
queue.addFirst("First");  // O(1)
queue.addLast("Last");    // O(1)
String head = queue.removeFirst(); // O(1)

// ❌ ANTI-PATTERN: Random access on LinkedList
LinkedList<String> list = getLargeList();
for (int i = 0; i < list.size(); i++) {
    // Triggers O(n) traversal from head EACH loop.
    // Total execution time explodes to O(n²)!
    String item = list.get(i);
}

// ✅ BEST PRACTICE: Iterator avoids re-traversing
for (String item : list) {
    process(item); // O(n) total execution time
}"""

    add_code_block(slide, Inches(6.2), Inches(1.2), Inches(3.3), Inches(2.9), code)

def add_slide_06(prs):
    """Slide 6: ArrayList vs LinkedList - The Verdict."""
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "ArrayList vs LinkedList - The Verdict", 6)

    # Create table
    from pptx.util import Inches
    rows, cols = 6, 4
    table_shape = slide.shapes.add_table(rows, cols, Inches(0.5), Inches(1.3), Inches(9), Inches(2.5))
    table = table_shape.table

    # Set column widths
    table.columns[0].width = Inches(2.5)
    table.columns[1].width = Inches(2)
    table.columns[2].width = Inches(2)
    table.columns[3].width = Inches(2.5)

    # Header row
    headers = ["Operation", "ArrayList", "LinkedList", "Winner"]
    for i, header in enumerate(headers):
        cell = table.cell(0, i)
        cell.text = header
        cell.fill.solid()
        cell.fill.fore_color.rgb = COLOR_BLUE_PRIMARY
        p = cell.text_frame.paragraphs[0]
        p.font.size = Pt(12)
        p.font.bold = True
        p.font.color.rgb = COLOR_WHITE
        p.alignment = PP_ALIGN.CENTER

    # Data rows
    data = [
        ["Random Access", "0.002 ms", "2,500 ms", "ArrayList 1,000,000x faster!"],
        ["Add at End", "2 ms", "5 ms", "ArrayList 2.5x faster"],
        ["Add at Beginning", "150 ms", "0.001 ms", "LinkedList 150,000x faster"],
        ["Iteration", "Fast (cache)", "Slow (pointers)", "ArrayList 3-5x faster"],
        ["Memory (1000 ints)", "~4 KB", "~40 KB", "ArrayList 10x more efficient"]
    ]

    for i, row_data in enumerate(data, start=1):
        for j, value in enumerate(row_data):
            cell = table.cell(i, j)
            cell.text = value
            p = cell.text_frame.paragraphs[0]
            p.font.size = Pt(10)
            p.alignment = PP_ALIGN.CENTER

            if i % 2 == 0:
                cell.fill.solid()
                cell.fill.fore_color.rgb = COLOR_SLATE_50

    # Verdict box
    verdict_box = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(1), Inches(4.1), Inches(8), Inches(0.7)
    )
    verdict_box.fill.solid()
    verdict_box.fill.fore_color.rgb = COLOR_BLUE_PRIMARY
    verdict_box.line.fill.background()

    tf = verdict_box.text_frame
    p = tf.paragraphs[0]
    p.text = "🎯 The Verdict: Default to ArrayList. Use LinkedList only for specific addFirst/removeFirst queue operations!"
    p.font.size = Pt(16)
    p.font.bold = True
    p.font.color.rgb = COLOR_WHITE
    p.alignment = PP_ALIGN.CENTER

def add_slide_07_to_14(prs):
    """Add remaining slides (simplified versions for brevity)."""

    # Slide 7: HashSet
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "HashSet - What/Why/When", 7)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(1.5),
             "📌 What is HashSet?",
             "Backed by HashMap. Default capacity 16, load factor 0.75. No guaranteed order. O(1) add/remove/contains. Converts bins to trees (Java 8+) for collision mitigation.")
    add_card(slide, Inches(0.5), Inches(2.8), Inches(2), Inches(1.3),
             "💡 Why / When to Use",
             ["✅ Automatic uniqueness", "✅ O(1) instant lookups", "✅ Fast add/remove", "✅ Set ops (union/intersection)"],
             COLOR_GREEN_50, COLOR_GREEN_500)
    add_card(slide, Inches(2.7), Inches(2.8), Inches(2.3), Inches(1.3),
             "❌ When NOT to Use",
             ["❌ Need ordering", "❌ Allow duplicates", "❌ Need index access"],
             COLOR_RED_50, COLOR_RED_500)
    code = """// GOOD: Remove duplicates
List<String> dupes = Arrays.asList("A", "B", "A");
Set<String> unique = new HashSet<>(dupes);

// GOOD: Fast membership check
Set<String> valid = new HashSet<>(List.of("US"));
if (valid.contains("US")) { // O(1) Instant!
    process();
}

// GOOD: Set operations
Set<Integer> set1 = new HashSet<>(List.of(1, 2, 3));
Set<Integer> set2 = new HashSet<>(List.of(3, 4, 5));
set1.retainAll(set2); // Intersection: {3}"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 8: Set Implementations Comparison
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Set Implementations - Comparison", 8)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(3), Inches(2.5),
             "LinkedHashSet",
             ["HashSet + insertion order", "✅ Need unique + insertion order", "✅ Deterministic testing", "❌ Slower than HashSet", "100k ops: 52 ms"])
    add_card(slide, Inches(3.7), Inches(1.2), Inches(3), Inches(2.5),
             "TreeSet",
             ["Red-Black Tree, sorted", "✅ Need sorted unique collection", "✅ Range ops (ceiling, floor, subSet)", "❌ Slower, O(log n) performance", "100k ops: 180 ms"])
    add_card(slide, Inches(6.9), Inches(1.2), Inches(2.6), Inches(2.5),
             "EnumSet",
             ["Bit vector for enums", "✅ ALWAYS use for enum sets", "✅ 64x more memory efficient", "✅ Lightning fast (bitwise ops)", "100k ops: 5 ms"],
             COLOR_ORANGE_LIGHT, COLOR_ORANGE_PRIMARY)

    # Slide 9: hashCode & equals Contract
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "hashCode() & equals() Contract", 9)
    info_box = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(0.5), Inches(1.2), Inches(4.5), Inches(2.2)
    )
    info_box.fill.solid()
    info_box.fill.fore_color.rgb = COLOR_BLUE_PRIMARY
    info_box.line.fill.background()
    tf = info_box.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_top = Inches(0.1)
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "The Mathematical Contract:\n"
    run.font.size = Pt(14)
    run.font.bold = True
    run.font.color.rgb = COLOR_ORANGE_PRIMARY
    for item in ["1. If a.equals(b) is true → a.hashCode() == b.hashCode() MUST be true",
                 "2. If a.hashCode() != b.hashCode() → a.equals(b) MUST be false",
                 "3. Consistency: Multiple invocations return same value if object unchanged"]:
        p = tf.add_paragraph()
        p.text = item
        p.font.size = Pt(10)
        p.font.color.rgb = COLOR_WHITE

    code = """class Person {
    private final String id;
    private final String name;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person p)) return false;
        return Objects.equals(id, p.id);
    }

    @Override
    public int hashCode() {
        // Must hash the same fields used in equals
        return Objects.hash(id);
    }
}"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 10: HashMap
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "HashMap - What/Why/When", 10)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(1.5),
             "📌 The Workhorse Map",
             "Array of buckets. Default load factor 0.75. O(1) average operations. Allows one null key and multiple null values.")
    add_card(slide, Inches(0.5), Inches(2.8), Inches(2), Inches(1.3),
             "💡 Why / When to Use",
             ["✅ O(1) lookups/indexing", "✅ Counting frequencies", "✅ Associating relational data"],
             COLOR_GREEN_50, COLOR_GREEN_500)
    add_card(slide, Inches(2.7), Inches(2.8), Inches(2.3), Inches(1.3),
             "❌ When NOT to Use",
             ["❌ Need sorted/insertion order", "❌ Thread-safe access needed"],
             COLOR_RED_50, COLOR_RED_500)
    code = """// ✅ BEST PRACTICE: Frequency counting
Map<String, Integer> freq = new HashMap<>();
for (String word : words) {
    freq.merge(word, 1, Integer::sum);
}

// ✅ BEST PRACTICE: Caching expensive ops
Map<String, Result> cache = new HashMap<>();
Result r = cache.computeIfAbsent(key,
    k -> expensiveOperation(k));

// ❌ ANTI-PATTERN: Old 'get-check-put'
if(!map.containsKey(key)) {
    map.put(key, new ArrayList<>());
}
map.get(key).add(value);"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 11: LinkedHashMap
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "LinkedHashMap - Predictable Iteration", 11)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(2.9),
             "Two Ordering Modes:",
             ["1. Insertion order (default): Elements returned in sequence they were added. Crucial for config files or JSON serialization.",
              "2. Access order (LRU): Most recently accessed element moves to end. Essential for memory caching."])
    code = """class LRUCache<K,V> extends LinkedHashMap<K,V> {
    private final int capacity;

    LRUCache(int capacity) {
        // capacity, load factor, accessOrder = true
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(
            Map.Entry<K,V> eldest) {
        // Automatically prune old entries
        return size() > capacity;
    }
}"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 12: TreeMap
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "TreeMap - Sorted & Navigable", 12)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(2.9),
             "📌 Red-Black Tree Implementation",
             ["O(log n) for get/put/remove. Sorted by keys. No null keys allowed.",
              "NavigableMap Operations:",
              "• ceilingKey(x) >= x",
              "• floorKey(x) <= x",
              "• higherKey(x) > x",
              "• lowerKey(x) < x",
              "• subMap(from, to) / headMap() / tailMap()"])
    code = """TreeMap<Integer, String> map = new TreeMap<>();
map.put(10, "A");
map.put(20, "B");
map.put(30, "C");

// Navigation Queries
map.ceilingKey(15); // Returns 20
map.floorKey(25);   // Returns 20

// Range Queries
map.tailMap(20); // Returns {20=B, 30=C}

// Time-based events processor
TreeMap<LocalDateTime, Event> schedule = ...;"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 13: ConcurrentHashMap
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "ConcurrentHashMap - What/Why/When", 13)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(1.5),
             "📌 Thread-Safe High-Performance Map",
             "Lock-free reads. CAS + bin-level locking for writes (no global lock). Iterators don't throw ConcurrentModificationException.")
    add_card(slide, Inches(0.5), Inches(2.8), Inches(2), Inches(1.3),
             "💡 When to Use",
             ["✅ Multi-threaded shared maps", "✅ High read concurrency", "✅ Atomic updates needed"],
             COLOR_GREEN_50, COLOR_GREEN_500)
    add_card(slide, Inches(2.7), Inches(2.8), Inches(2.3), Inches(1.3),
             "❌ When NOT to Use",
             ["❌ Single-threaded (overhead)", "❌ Need null keys/values"],
             COLOR_RED_50, COLOR_RED_500)
    code = """// GOOD: Atomic thread-safe counter
ConcurrentHashMap<String, Long> counters =
    new ConcurrentHashMap<>();

// No synchronized block needed!
counters.merge("requests", 1L, Long::sum);

// GOOD: Thread-safe caching
ConcurrentHashMap<String, User> cache =
    new ConcurrentHashMap<>();
User u = cache.computeIfAbsent(id,
    this::loadUser);"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 14: Java 8+ Map Compute Methods
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Java 8+ Map Compute Methods", 14)

    methods = [
        ("compute()", "Always computes new value (whether key is present or absent)", "map.compute(\"key\", (k, v) -> v == null ? 1 : v + 1);"),
        ("computeIfAbsent()", "Computes value only if key is absent", "map.computeIfAbsent(key, k -> expensiveOp(k));"),
        ("computeIfPresent()", "Computes value only if key is present", "map.computeIfPresent(key, (k, v) -> v + 1);"),
        ("merge()", "Merges new value with existing value using function", "map.merge(key, 1, Integer::sum);")
    ]

    y_offset = 1.2
    for method, desc, example in methods:
        card_box = slide.shapes.add_shape(
            MSO_SHAPE.ROUNDED_RECTANGLE, Inches(0.5), Inches(y_offset), Inches(9), Inches(0.65)
        )
        card_box.fill.solid()
        card_box.fill.fore_color.rgb = COLOR_SLATE_50
        card_box.line.color.rgb = COLOR_BLUE_ACCENT
        card_box.line.width = Pt(2)

        tf = card_box.text_frame
        tf.word_wrap = True
        tf.margin_left = Inches(0.1)
        tf.margin_top = Inches(0.05)

        p = tf.paragraphs[0]
        run = p.add_run()
        run.text = f"{method}\n"
        run.font.size = Pt(12)
        run.font.bold = True
        run.font.color.rgb = COLOR_BLUE_PRIMARY

        p = tf.add_paragraph()
        p.text = f"{desc}\n{example}"
        p.font.size = Pt(9)
        p.font.color.rgb = COLOR_SLATE_700

        y_offset += 0.75

def add_slide_15_to_27(prs):
    """Add remaining slides 15-27."""

    # Slide 15: Queue & Deque
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Queue & Deque - FIFO & LIFO", 15)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(0.8),
             "1. ArrayDeque (Best Stack/Queue)",
             "Resizable circular array, O(1) both ends. Vastly outperforms legacy Stack.")
    add_card(slide, Inches(0.5), Inches(2.1), Inches(4.5), Inches(0.8),
             "2. PriorityQueue (Heaps)",
             "Binary heap. Smallest/highest-priority element first. O(log n) inserts. Top-K algorithms.")
    add_card(slide, Inches(0.5), Inches(3.0), Inches(4.5), Inches(0.8),
             "3. BlockingQueue",
             "Thread-safe producer-consumer wait/notify patterns.")
    code = """// ArrayDeque as Stack
Deque<String> stack = new ArrayDeque<>();
stack.push("First");
String top = stack.pop();

// Priority Task Queue
PriorityQueue<Task> tasks = new PriorityQueue<>(
    Comparator.comparingInt(Task::getPriority)
);
tasks.offer(new Task("Urgent", 1));
Task t = tasks.poll(); // gets priority 1

// Producer/Consumer
BlockingQueue<Work> q = new ArrayBlockingQueue<>(100);
q.put(work); // Blocks if full
Work w = q.take(); // Blocks if empty"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 16: Immutable Collections
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Immutable Collections (Java 9+)", 16)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(1.0),
             "📌 True Immutability",
             "List.of(), Set.of(), Map.of(). Not merely read-only views. Completely independent, thread-safe, and highly space-efficient.")
    add_card(slide, Inches(0.5), Inches(2.3), Inches(2.1), Inches(1.0),
             "💡 Why to Use",
             ["✅ Thread-safe by default", "✅ No defensive copies", "✅ JVM memory optimizations"],
             COLOR_GREEN_50, COLOR_GREEN_500)
    add_card(slide, Inches(2.8), Inches(2.3), Inches(2.2), Inches(1.0),
             "⚠️ Constraints",
             ["• No nulls allowed", "• Fails fast on modification"])
    code = """// GOOD: System Constants
private static final List<String> STATUS =
    List.of("PENDING", "APPROVED", "REJECTED");

// GOOD: Return safely without copying
public List<String> getPermissions() {
    return List.of("READ", "WRITE");
}

// GOOD: Build then freeze
List<String> temp = new ArrayList<>();
// ... logic ...
List<String> frozen = List.copyOf(temp);"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 17: Comparable vs Comparator
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Comparable vs Comparator", 17)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(1.2),
             "Comparable<T> (Natural Ordering)",
             ["• Implemented by the class itself.", "• Single ordering logic (compareTo).", "• e.g., String, Integer, LocalDate."])
    add_card(slide, Inches(0.5), Inches(2.5), Inches(4.5), Inches(1.2),
             "Comparator<T> (External)",
             ["• External to class; multiple orderings possible.", "• Highly fluent Java 8+ API for chaining.", "• Use primitives (comparingInt) to stop boxing."],
             COLOR_ORANGE_LIGHT, COLOR_ORANGE_PRIMARY)
    code = """// Advanced Comparator Chaining
Comparator<Employee> comp = Comparator
    .comparingInt(Employee::getAge)
    .thenComparingDouble(Employee::getSalary)
    .thenComparing(Employee::getName)
    .reversed();

// Handling Nulls
Comparator<User> c2 = Comparator
    .comparing(User::getLastName,
               Comparator.nullsLast(
                   String.CASE_INSENSITIVE_ORDER));"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 18: Performance Summary - Decision Matrix
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Performance Summary - Decision Matrix", 18)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(2.9),
             "List & Set Hierarchy",
             ["ArrayList: Default. Random access, iteration, append.",
              "LinkedList: ONLY for deque ops at both ends.",
              "HashSet: Default Set. Uniqueness, O(1).",
              "LinkedHashSet: Insertion order maintained.",
              "TreeSet: Sorted order, range ops.",
              "EnumSet: Enum types only. Fastest possible Set."])
    add_card(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9),
             "Map & Queue Hierarchy",
             ["HashMap: Default general purpose Map.",
              "LinkedHashMap: Insertion/access order (LRU).",
              "TreeMap: Sorted keys, range queries.",
              "ConcurrentHashMap: Thread-safe, high concurrency.",
              "ArrayDeque: Best Stack/Queue performance.",
              "PriorityQueue: Min/Max heap logic."],
             COLOR_ORANGE_LIGHT, COLOR_ORANGE_PRIMARY)

    # Slide 19: Stream API
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Stream API - Declarative Pipelines", 19)
    info_box = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(0.5), Inches(1.2), Inches(4.5), Inches(1.5)
    )
    info_box.fill.solid()
    info_box.fill.fore_color.rgb = COLOR_BLUE_PRIMARY
    info_box.line.fill.background()
    tf = info_box.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_top = Inches(0.1)
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "📌 What is a Stream?\n"
    run.font.size = Pt(14)
    run.font.bold = True
    run.font.color.rgb = COLOR_ORANGE_PRIMARY
    p = tf.add_paragraph()
    p.text = "NOT a data structure. It holds no storage. It is a one-time-use pipeline descriptor that conveys elements lazily."
    p.font.size = Pt(10)
    p.font.color.rgb = COLOR_WHITE

    add_card(slide, Inches(0.5), Inches(2.8), Inches(2.1), Inches(1.3),
             "💡 Why / When to Use",
             ["✅ Declarative code (what, not how)", "✅ Easy multi-core parallelization", "✅ Processing collections (map/reduce)"],
             COLOR_GREEN_50, COLOR_GREEN_500)
    add_card(slide, Inches(2.8), Inches(2.8), Inches(2.2), Inches(1.3),
             "❌ Anti-Patterns",
             ["❌ Modifying source inside .forEach()", "❌ Overusing streams for simple iterations", "❌ Complex try/catch in lambdas"],
             COLOR_RED_50, COLOR_RED_500)

    code = """// ❌ ANTI-PATTERN: Stream with side-effects
List<String> badResult = new ArrayList<>();
names.stream()
     .filter(n -> n.length() > 3)
     .forEach(n -> badResult.add(n.toUpperCase()));

// ✅ BEST PRACTICE: Declarative & Pure
List<String> goodResult = names.stream()
    .filter(Objects::nonNull)
    .filter(n -> n.length() > 3)
    .map(String::toUpperCase)
    .sorted()
    .collect(Collectors.toList());"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 20: Stream Operations Quick Reference
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Stream Operations - Quick Reference", 20)

    code1 = """.filter(n -> n > 10)       // Predicate match
.map(String::toUpperCase)  // Transform
.flatMap(List::stream)     // Flatten nesting
.distinct()                // Unique items
.sorted()                  // Natural order
.limit(5)                  // Max 5 items
.skip(3)                   // Bypass 3 items"""

    code2 = """.collect(toList())         // Gather to list
.forEach(System.out::print)// Side-effect
.reduce(0, Integer::sum)   // Aggregate math
.count()                   // Size of stream
.anyMatch(n -> n > 5)      // Boolean check
.findFirst()               // Returns Optional"""

    card1 = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(0.5), Inches(1.2), Inches(4.5), Inches(2.5)
    )
    card1.fill.solid()
    card1.fill.fore_color.rgb = COLOR_SLATE_50
    card1.line.color.rgb = COLOR_BLUE_ACCENT
    card1.line.width = Pt(2)
    tf = card1.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_top = Inches(0.1)
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "Intermediate (Lazy, return Stream)\n\n"
    run.font.size = Pt(14)
    run.font.bold = True
    run.font.color.rgb = COLOR_BLUE_PRIMARY
    p = tf.add_paragraph()
    p.text = code1
    p.font.name = 'Courier New'
    p.font.size = Pt(9)
    p.font.color.rgb = COLOR_BLUE_DARK

    card2 = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.5)
    )
    card2.fill.solid()
    card2.fill.fore_color.rgb = COLOR_ORANGE_LIGHT
    card2.line.color.rgb = COLOR_ORANGE_PRIMARY
    card2.line.width = Pt(2)
    tf = card2.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_top = Inches(0.1)
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "Terminal (Eager, return Result)\n\n"
    run.font.size = Pt(14)
    run.font.bold = True
    run.font.color.rgb = COLOR_ORANGE_PRIMARY
    p = tf.add_paragraph()
    p.text = code2
    p.font.name = 'Courier New'
    p.font.size = Pt(9)
    p.font.color.rgb = COLOR_BLUE_DARK

    # Slide 21: Lazy Evaluation & Short-Circuiting
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Lazy Evaluation & Short-Circuiting", 21)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(1.2),
             "Lazy Evaluation",
             "Pipeline operations are fused and pushed to the JVM. Nothing processes until the terminal operation runs.")
    add_card(slide, Inches(0.5), Inches(2.5), Inches(4.5), Inches(1.6),
             "Short-Circuiting",
             ["Operations that complete early without processing the entire stream.",
              "• Intermediate: limit(n), takeWhile()",
              "• Terminal: findFirst(), anyMatch()"],
             COLOR_ORANGE_LIGHT, COLOR_ORANGE_PRIMARY)
    code = """// Optimization Demonstration
list.stream()
    .filter(n -> {
        // Will ONLY print until first even is found
        System.out.println("Checking " + n);
        return n % 2 == 0;
    })
    .map(n -> n * 2)
    .findFirst(); // Terminals trigger the pipeline!

// Massive memory/CPU savings on 1M element list!"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 22: Advanced Collectors
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Advanced Collectors", 22)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(4.5), Inches(2.9),
             "Powerful Groupings",
             ["Collectors execute mutable reduction. The Collectors class provides SQL-like data aggregation.",
              "",
              "• groupingBy: The equivalent of SQL GROUP BY.",
              "• partitioningBy: Binary classification (true/false keys).",
              "• Downstream: Applying secondary collectors (like averaging) to groups."])
    code = """// SQL GROUP BY equivalent
Map<String, List<Employee>> byDept =
    employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment));

// Binary Partitioning (Even/Odd)
Map<Boolean, List<Integer>> split =
    nums.stream()
    .collect(Collectors.partitioningBy(
        n -> n % 2 == 0));

// Downstream Grouping & Math
Map<String, Double> avgSalaryByDept =
    emps.stream()
    .collect(Collectors.groupingBy(
        Employee::getDept,
        Collectors.averagingDouble(Employee::getSal)
    ));"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 23: reduce vs collect
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "reduce vs collect", 23)

    card1 = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(0.5), Inches(1.2), Inches(4.5), Inches(2.9)
    )
    card1.fill.solid()
    card1.fill.fore_color.rgb = COLOR_ORANGE_LIGHT
    card1.line.color.rgb = COLOR_ORANGE_PRIMARY
    card1.line.width = Pt(3)
    tf = card1.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_top = Inches(0.1)
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "reduce() - Immutable Reduction\n\n"
    run.font.size = Pt(16)
    run.font.bold = True
    run.font.color.rgb = COLOR_ORANGE_PRIMARY
    for item in ["• Creates a new value at each step.",
                 "• Perfect for primitives and simple aggregations (sum, min, max).",
                 "• Has an identity, accumulator, and combiner (for parallel).",
                 "",
                 "int sum = numbers.stream().reduce(0, Integer::sum);"]:
        p = tf.add_paragraph()
        p.text = item
        p.font.size = Pt(10)
        p.font.color.rgb = COLOR_SLATE_700
        if "int sum" in item:
            p.font.name = 'Courier New'

    card2 = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9)
    )
    card2.fill.solid()
    card2.fill.fore_color.rgb = RGBColor(239, 246, 255)
    card2.line.color.rgb = COLOR_BLUE_ACCENT
    card2.line.width = Pt(3)
    tf = card2.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_top = Inches(0.1)
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "collect() - Mutable Reduction\n\n"
    run.font.size = Pt(16)
    run.font.bold = True
    run.font.color.rgb = COLOR_BLUE_PRIMARY
    for item in ["• Mutates a container (e.g., ArrayList) repeatedly.",
                 "• Orders of magnitude faster for gathering elements.",
                 "• Handles complex grouping logic cleanly.",
                 "",
                 "List<String> list = stream.collect(Collectors.toList());"]:
        p = tf.add_paragraph()
        p.text = item
        p.font.size = Pt(10)
        p.font.color.rgb = COLOR_SLATE_700
        if "List<String>" in item:
            p.font.name = 'Courier New'

    # Slide 24: Parallel Streams
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Parallel Streams - Power & Pitfalls", 24)
    add_card(slide, Inches(0.5), Inches(1.2), Inches(2.1), Inches(1.2),
             "✅ When to Parallelize",
             ["• Huge Datasets (>10k elements)", "• CPU-intensive map/filter ops", "• Purely stateless operations"],
             COLOR_GREEN_50, COLOR_GREEN_500)
    add_card(slide, Inches(2.8), Inches(1.2), Inches(2.2), Inches(1.2),
             "❌ When NOT to Use",
             ["• I/O Bound logic (HTTP/DB)", "• Tiny collections (threading overhead)", "• Shared mutable state"],
             COLOR_RED_50, COLOR_RED_500)

    warning = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(0.5), Inches(2.5), Inches(4.5), Inches(0.7)
    )
    warning.fill.solid()
    warning.fill.fore_color.rgb = COLOR_BLUE_PRIMARY
    warning.line.fill.background()
    tf = warning.text_frame
    tf.word_wrap = True
    p = tf.paragraphs[0]
    p.text = "Under the hood, .parallelStream() uses the JVM's global ForkJoinPool.commonPool(). Misusing it can stall your entire application."
    p.font.size = Pt(10)
    p.font.color.rgb = COLOR_WHITE

    code = """// GOOD: Functional aggregation
int correctSum = numbers.parallelStream()
    .reduce(0, Integer::sum); // Safe!

// GOOD: CPU-intensive isolation
List<Image> processed = images
    .parallelStream()
    .map(this::heavyImageFilters)
    .collect(Collectors.toList());

// Can achieve 4-8x speedup on multicore"""
    add_code_block(slide, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9), code)

    # Slide 25: Parallel Stream Pitfalls
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Parallel Stream Pitfalls & Solutions", 25)

    code1 = """// ❌ WRONG: Race Condition
int[] sum = {0};
IntStream.range(0, 1000).parallel()
    .forEach(i -> sum[0] += i);
// Yields unpredictable incorrect results!

// ✅ CORRECT: Use functional reduce
int sum = IntStream.range(0, 1000).parallel()
    .reduce(0, Integer::sum);"""

    code2 = """// ❌ WRONG: ArrayList is not concurrent
List<Integer> list = new ArrayList<>();
IntStream.range(0, 1000).parallel()
    .forEach(list::add);
// Throws exceptions & loses data!

// ✅ CORRECT: Let Collect handle threading
List<Integer> list = IntStream.range(0, 1000)
    .parallel()
    .boxed()
    .collect(Collectors.toList());"""

    card1 = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(0.5), Inches(1.2), Inches(4.5), Inches(2.9)
    )
    card1.fill.solid()
    card1.fill.fore_color.rgb = COLOR_RED_50
    card1.line.color.rgb = COLOR_RED_500
    card1.line.width = Pt(3)
    tf = card1.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_top = Inches(0.1)
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "Pitfall 1: Shared Mutable State\n\n"
    run.font.size = Pt(14)
    run.font.bold = True
    run.font.color.rgb = COLOR_RED_700
    p = tf.add_paragraph()
    p.text = code1
    p.font.name = 'Courier New'
    p.font.size = Pt(8)
    p.font.color.rgb = COLOR_BLUE_DARK

    card2 = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(5.2), Inches(1.2), Inches(4.3), Inches(2.9)
    )
    card2.fill.solid()
    card2.fill.fore_color.rgb = COLOR_RED_50
    card2.line.color.rgb = COLOR_RED_500
    card2.line.width = Pt(3)
    tf = card2.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_top = Inches(0.1)
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "Pitfall 2: Non-Thread-Safe Collections\n\n"
    run.font.size = Pt(14)
    run.font.bold = True
    run.font.color.rgb = COLOR_RED_700
    p = tf.add_paragraph()
    p.text = code2
    p.font.name = 'Courier New'
    p.font.size = Pt(8)
    p.font.color.rgb = COLOR_BLUE_DARK

    # Slide 26: Key Takeaways
    slide = prs.slides.add_slide(prs.slide_layouts[6])
    add_header(slide, "Key Takeaways & Best Practices", 26)

    add_card(slide, Inches(0.5), Inches(1.2), Inches(3), Inches(2.9),
             "Collections",
             ["1. Program to interfaces: List<T>.",
              "2. ArrayList = Default List, HashSet = Default Set.",
              "3. Specify initial capacity if known.",
              "4. Override hashCode() with equals().",
              "5. Use List.of() for immutability.",
              "6. Use EnumSet/EnumMap for enums.",
              "7. ConcurrentHashMap over synchronized block."])

    add_card(slide, Inches(3.7), Inches(1.2), Inches(3), Inches(2.9),
             "Streams",
             ["1. Use for complex data transformations.",
              "2. Prefer method references over lambdas.",
              "3. Use primitive streams (IntStream).",
              "4. collect() over reduce() for gathering.",
              "5. Avoid side-effects inside pipelines.",
              "6. Use Optional correctly (not for collections)."],
             COLOR_ORANGE_LIGHT, COLOR_ORANGE_PRIMARY)

    perf_box = slide.shapes.add_shape(
        MSO_SHAPE.ROUNDED_RECTANGLE, Inches(6.9), Inches(1.2), Inches(2.6), Inches(2.9)
    )
    perf_box.fill.solid()
    perf_box.fill.fore_color.rgb = COLOR_BLUE_PRIMARY
    perf_box.line.fill.background()
    tf = perf_box.text_frame
    tf.word_wrap = True
    tf.margin_left = Inches(0.15)
    tf.margin_top = Inches(0.1)
    p = tf.paragraphs[0]
    run = p.add_run()
    run.text = "Performance Rules\n\n"
    run.font.size = Pt(14)
    run.font.bold = True
    run.font.color.rgb = COLOR_ORANGE_PRIMARY
    for item in ["▶ Choose the right collection - it impacts performance by orders of magnitude.",
                 "",
                 "▶ Don't parallelize prematurely - large datasets + CPU-intensive only.",
                 "",
                 "▶ Measure, don't assume - utilize JMH benchmarks.",
                 "",
                 "Questions?"]:
        p = tf.add_paragraph()
        p.text = item
        p.font.size = Pt(9) if "▶" in item else Pt(16)
        p.font.color.rgb = COLOR_WHITE if "▶" in item else COLOR_ORANGE_PRIMARY
        if "Questions" in item:
            p.font.bold = True
            p.alignment = PP_ALIGN.CENTER

def main():
    """Main function to generate the PPTX presentation."""
    print("Creating Java Collections & Streams presentation...")

    prs = create_presentation()

    # Add all slides
    add_title_slide(prs)
    add_slide_02(prs)
    add_slide_03(prs)
    add_slide_04(prs)
    add_slide_05(prs)
    add_slide_06(prs)
    add_slide_07_to_14(prs)
    add_slide_15_to_27(prs)

    # Save the presentation
    output_file = 'java-practice/java-collections/java_collections_streams_deep_dive.pptx'
    prs.save(output_file)

    print(f"✅ Presentation created successfully: {output_file}")
    print(f"📊 Total slides: {len(prs.slides)}")
    print("\nTo use in Google Slides:")
    print("1. Go to Google Slides")
    print("2. Click File → Import slides")
    print("3. Upload the PPTX file")
    print("4. Select all slides to import")

if __name__ == "__main__":
    main()
