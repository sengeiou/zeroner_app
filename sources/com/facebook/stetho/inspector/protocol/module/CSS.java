package com.facebook.stetho.inspector.protocol.module;

import android.annotation.SuppressLint;
import com.facebook.stetho.common.ListUtil;
import com.facebook.stetho.common.LogUtil;
import com.facebook.stetho.common.StringUtil;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.elements.ComputedStyleAccumulator;
import com.facebook.stetho.inspector.elements.Document;
import com.facebook.stetho.inspector.elements.Origin;
import com.facebook.stetho.inspector.elements.StyleAccumulator;
import com.facebook.stetho.inspector.elements.StyleRuleNameAccumulator;
import com.facebook.stetho.inspector.helper.ChromePeerManager;
import com.facebook.stetho.inspector.helper.PeersRegisteredListener;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcPeer;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcResult;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsMethod;
import com.facebook.stetho.json.ObjectMapper;
import com.facebook.stetho.json.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class CSS implements ChromeDevtoolsDomain {
    /* access modifiers changed from: private */
    public final Document mDocument;
    private final ObjectMapper mObjectMapper = new ObjectMapper();
    private final ChromePeerManager mPeerManager = new ChromePeerManager();

    private static class CSSComputedStyleProperty {
        @JsonProperty(required = true)
        public String name;
        @JsonProperty(required = true)
        public String value;

        private CSSComputedStyleProperty() {
        }
    }

    private static class CSSProperty {
        @JsonProperty
        public Boolean disabled;
        @JsonProperty
        public Boolean implicit;
        @JsonProperty
        public Boolean important;
        @JsonProperty(required = true)
        public String name;
        @JsonProperty
        public Boolean parsedOk;
        @JsonProperty
        public SourceRange range;
        @JsonProperty
        public String text;
        @JsonProperty(required = true)
        public String value;

        private CSSProperty() {
        }
    }

    private static class CSSRule {
        @JsonProperty
        public Origin origin;
        @JsonProperty(required = true)
        public SelectorList selectorList;
        @JsonProperty
        public CSSStyle style;
        @JsonProperty
        public String styleSheetId;

        private CSSRule() {
        }
    }

    private static class CSSStyle {
        @JsonProperty(required = true)
        public List<CSSProperty> cssProperties;
        @JsonProperty
        public String cssText;
        @JsonProperty
        public SourceRange range;
        @JsonProperty
        public List<ShorthandEntry> shorthandEntries;
        @JsonProperty
        public String styleSheetId;

        private CSSStyle() {
        }
    }

    private static class GetComputedStyleForNodeRequest {
        @JsonProperty(required = true)
        public int nodeId;

        private GetComputedStyleForNodeRequest() {
        }
    }

    private static class GetComputedStyleForNodeResult implements JsonRpcResult {
        @JsonProperty(required = true)
        public List<CSSComputedStyleProperty> computedStyle;

        private GetComputedStyleForNodeResult() {
        }
    }

    private static class GetMatchedStylesForNodeRequest implements JsonRpcResult {
        @JsonProperty
        public Boolean excludeInherited;
        @JsonProperty
        public Boolean excludePseudo;
        @JsonProperty(required = true)
        public int nodeId;

        private GetMatchedStylesForNodeRequest() {
        }
    }

    private static class GetMatchedStylesForNodeResult implements JsonRpcResult {
        @JsonProperty
        public List<InheritedStyleEntry> inherited;
        @JsonProperty
        public List<RuleMatch> matchedCSSRules;
        @JsonProperty
        public List<PseudoIdMatches> pseudoElements;

        private GetMatchedStylesForNodeResult() {
        }
    }

    private static class InheritedStyleEntry {
        @JsonProperty(required = true)
        public CSSStyle inlineStyle;
        @JsonProperty(required = true)
        public List<RuleMatch> matchedCSSRules;

        private InheritedStyleEntry() {
        }
    }

    private static class PseudoIdMatches {
        @JsonProperty(required = true)
        public List<RuleMatch> matches = new ArrayList();
        @JsonProperty(required = true)
        public int pseudoId;
    }

    private static class RuleMatch {
        @JsonProperty
        public List<Integer> matchingSelectors;
        @JsonProperty
        public CSSRule rule;

        private RuleMatch() {
        }
    }

    private static class Selector {
        @JsonProperty
        public SourceRange range;
        @JsonProperty(required = true)
        public String value;

        private Selector() {
        }
    }

    private static class SelectorList {
        @JsonProperty
        public List<Selector> selectors;
        @JsonProperty
        public String text;

        private SelectorList() {
        }
    }

    private static class SetPropertyTextRequest implements JsonRpcResult {
        @JsonProperty(required = true)
        public String styleSheetId;
        @JsonProperty(required = true)
        public String text;

        private SetPropertyTextRequest() {
        }
    }

    private static class SetPropertyTextResult implements JsonRpcResult {
        @JsonProperty(required = true)
        public CSSStyle style;

        private SetPropertyTextResult() {
        }
    }

    private static class ShorthandEntry {
        @JsonProperty
        public Boolean imporant;
        @JsonProperty(required = true)
        public String name;
        @JsonProperty(required = true)
        public String value;

        private ShorthandEntry() {
        }
    }

    private static class SourceRange {
        @JsonProperty(required = true)
        public int endColumn;
        @JsonProperty(required = true)
        public int endLine;
        @JsonProperty(required = true)
        public int startColumn;
        @JsonProperty(required = true)
        public int startLine;

        private SourceRange() {
        }
    }

    private final class PeerManagerListener extends PeersRegisteredListener {
        private PeerManagerListener() {
        }

        /* access modifiers changed from: protected */
        public synchronized void onFirstPeerRegistered() {
            CSS.this.mDocument.addRef();
        }

        /* access modifiers changed from: protected */
        public synchronized void onLastPeerUnregistered() {
            CSS.this.mDocument.release();
        }
    }

    public CSS(Document document) {
        this.mDocument = (Document) Util.throwIfNull(document);
        this.mPeerManager.setListener(new PeerManagerListener());
    }

    @ChromeDevtoolsMethod
    public void enable(JsonRpcPeer peer, JSONObject params) {
    }

    @ChromeDevtoolsMethod
    public void disable(JsonRpcPeer peer, JSONObject params) {
    }

    @ChromeDevtoolsMethod
    public JsonRpcResult getComputedStyleForNode(JsonRpcPeer peer, JSONObject params) {
        final GetComputedStyleForNodeRequest request = (GetComputedStyleForNodeRequest) this.mObjectMapper.convertValue(params, GetComputedStyleForNodeRequest.class);
        final GetComputedStyleForNodeResult result = new GetComputedStyleForNodeResult();
        result.computedStyle = new ArrayList();
        this.mDocument.postAndWait((Runnable) new Runnable() {
            public void run() {
                Object element = CSS.this.mDocument.getElementForNodeId(request.nodeId);
                if (element == null) {
                    LogUtil.e("Tried to get the style of an element that does not exist, using nodeid=" + request.nodeId);
                } else {
                    CSS.this.mDocument.getElementComputedStyles(element, new ComputedStyleAccumulator() {
                        public void store(String name, String value) {
                            CSSComputedStyleProperty property = new CSSComputedStyleProperty();
                            property.name = name;
                            property.value = value;
                            result.computedStyle.add(property);
                        }
                    });
                }
            }
        });
        return result;
    }

    @ChromeDevtoolsMethod
    @SuppressLint({"DefaultLocale"})
    public JsonRpcResult getMatchedStylesForNode(JsonRpcPeer peer, JSONObject params) {
        final GetMatchedStylesForNodeRequest request = (GetMatchedStylesForNodeRequest) this.mObjectMapper.convertValue(params, GetMatchedStylesForNodeRequest.class);
        final GetMatchedStylesForNodeResult result = new GetMatchedStylesForNodeResult();
        result.matchedCSSRules = new ArrayList();
        result.inherited = Collections.emptyList();
        result.pseudoElements = Collections.emptyList();
        this.mDocument.postAndWait((Runnable) new Runnable() {
            public void run() {
                final Object elementForNodeId = CSS.this.mDocument.getElementForNodeId(request.nodeId);
                if (elementForNodeId == null) {
                    LogUtil.w("Failed to get style of an element that does not exist, nodeid=" + request.nodeId);
                } else {
                    CSS.this.mDocument.getElementStyleRuleNames(elementForNodeId, new StyleRuleNameAccumulator() {
                        public void store(String ruleName, boolean editable) {
                            final ArrayList<CSSProperty> properties = new ArrayList<>();
                            RuleMatch match = new RuleMatch();
                            match.matchingSelectors = ListUtil.newImmutableList(Integer.valueOf(0));
                            Selector selector = new Selector();
                            selector.value = ruleName;
                            CSSRule rule = new CSSRule();
                            rule.origin = Origin.REGULAR;
                            rule.selectorList = new SelectorList();
                            rule.selectorList.selectors = ListUtil.newImmutableList(selector);
                            rule.style = new CSSStyle();
                            rule.style.cssProperties = properties;
                            rule.style.shorthandEntries = Collections.emptyList();
                            if (editable) {
                                rule.style.styleSheetId = String.format("%s.%s", new Object[]{Integer.toString(request.nodeId), selector.value});
                            }
                            CSS.this.mDocument.getElementStyles(elementForNodeId, ruleName, new StyleAccumulator() {
                                public void store(String name, String value, boolean isDefault) {
                                    CSSProperty property = new CSSProperty();
                                    property.name = name;
                                    property.value = value;
                                    properties.add(property);
                                }
                            });
                            match.rule = rule;
                            result.matchedCSSRules.add(match);
                        }
                    });
                }
            }
        });
        return result;
    }

    @ChromeDevtoolsMethod
    public SetPropertyTextResult setPropertyText(JsonRpcPeer peer, JSONObject params) {
        final String value;
        final String key;
        SetPropertyTextRequest request = (SetPropertyTextRequest) this.mObjectMapper.convertValue(params, SetPropertyTextRequest.class);
        String[] parts = request.styleSheetId.split("\\.", 2);
        final int nodeId = Integer.parseInt(parts[0]);
        final String ruleName = parts[1];
        if (request.text == null || !request.text.contains(":")) {
            key = null;
            value = null;
        } else {
            String[] keyValue = request.text.split(":", 2);
            key = keyValue[0].trim();
            value = StringUtil.removeAll(keyValue[1], ';').trim();
        }
        final SetPropertyTextResult result = new SetPropertyTextResult();
        result.style = new CSSStyle();
        result.style.styleSheetId = request.styleSheetId;
        result.style.cssProperties = new ArrayList();
        result.style.shorthandEntries = Collections.emptyList();
        this.mDocument.postAndWait((Runnable) new Runnable() {
            public void run() {
                Object elementForNodeId = CSS.this.mDocument.getElementForNodeId(nodeId);
                if (elementForNodeId == null) {
                    LogUtil.w("Failed to get style of an element that does not exist, nodeid=" + nodeId);
                    return;
                }
                if (key != null) {
                    CSS.this.mDocument.setElementStyle(elementForNodeId, ruleName, key, value);
                }
                CSS.this.mDocument.getElementStyles(elementForNodeId, ruleName, new StyleAccumulator() {
                    public void store(String name, String value, boolean isDefault) {
                        CSSProperty property = new CSSProperty();
                        property.name = name;
                        property.value = value;
                        result.style.cssProperties.add(property);
                    }
                });
            }
        });
        return result;
    }
}
