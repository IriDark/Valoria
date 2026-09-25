#version 150

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float GameTime;

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec2 uv = texCoord0;

    uv -= 0.5;

    float dist = length(uv);
    float angle = atan(uv.y, uv.x);

    float timeSpeed = GameTime * 1200.0;
    float swirlIntensity = (0.5 - dist) * 8.0;

    angle -= timeSpeed + swirlIntensity;

    uv = vec2(cos(angle), sin(angle)) * dist;
    uv += 0.5;

    if (uv.x < 0.0 || uv.x > 1.0 || uv.y < 0.0 || uv.y > 1.0) {
        discard;
    }

    vec4 color = texture(Sampler0, uv) * vertexColor * ColorModulator;
    float edgeFade = 1.0 - smoothstep(0.35, 0.5, dist);
    color.a *= edgeFade;

    if (color.a < 0.1) {
        discard;
    }

    fragColor = color;
}
