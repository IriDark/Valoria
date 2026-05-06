#version 150

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;

in vec4 vertexColor;
in vec2 texCoord0;
in vec3 v_normal;
in vec4 v_pos;

out vec4 fragColor;

float hash(vec2 p) {
    return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453);
}

void main() {
    vec4 texColor = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (texColor.a < 0.1) discard;

    vec2 texSize = vec2(textureSize(Sampler0, 0));
    vec2 offset = 1.0 / texSize;

    float aL = texture(Sampler0, texCoord0 + vec2(-offset.x, 0.0)).a;
    float aR = texture(Sampler0, texCoord0 + vec2(offset.x, 0.0)).a;
    float aU = texture(Sampler0, texCoord0 + vec2(0.0, -offset.y)).a;
    float aD = texture(Sampler0, texCoord0 + vec2(0.0, offset.y)).a;
    float aC = texColor.a;

    float edgeDetect = 0.0;
    if (aC > 0.5) {
        float minAlpha = min(min(aL, aR), min(aU, aD));
        edgeDetect = aC - minAlpha;
    }

    float outline = smoothstep(0.3, 0.6, edgeDetect);

    float noise = hash(texCoord0 * 200.0);
    outline = outline * (0.7 + noise * 0.3);

    vec3 normal = normalize(v_normal);
    vec3 lightDir = normalize(vec3(1.0, 1.5, -1.0));
    float diff = max(dot(normal, lightDir), 0.0);
    float shading = diff * 0.5 + 0.5;

    vec3 paperColor = vec3(0.95, 0.94, 0.92);
    vec3 lineColor = vec3(0.2, 0.2, 0.22);

    vec3 baseColor = mix(texColor.rgb, paperColor, 0.3);
    baseColor = baseColor * shading;

    vec3 finalColor = mix(baseColor, lineColor, outline * 0.7);

    fragColor = vec4(finalColor, texColor.a * ColorModulator.a);
}
