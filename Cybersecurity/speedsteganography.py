from PIL import Image
import os

def encode_text(image_path, output_name, secret_text):
    image = Image.open(image_path)
    if image.mode != 'RGB':
        image = image.convert('RGB')
    data = list(image.getdata())
    binary_text = ''.join(format(ord(c), '08b') for c in secret_text) + '1111111111111110'  # EOF marker
    binary_index = 0

    for i in range(len(data)):
        r, g, b = data[i]
        if binary_index < len(binary_text):
            r = (r & ~1) | int(binary_text[binary_index])
            binary_index += 1
        if binary_index < len(binary_text):
            g = (g & ~1) | int(binary_text[binary_index])
            binary_index += 1
        if binary_index < len(binary_text):
            b = (b & ~1) | int(binary_text[binary_index])
            binary_index += 1
        data[i] = (r, g, b)

    if binary_index < len(binary_text):
        raise ValueError("Text is too long to fit in this image!")
    encoded_image = Image.new(image.mode, image.size)
    encoded_image.putdata(data)
    output_path = os.path.join(os.path.dirname(image_path), f"{output_name}.png")
    encoded_image.save(output_path)
    print(f"Text successfully encoded and saved as {output_path}")

def decode_text(image_path):
    image = Image.open(image_path)
    data = list(image.getdata())
    binary_text = ""
    for r, g, b in data:
        binary_text += str(r & 1)
        binary_text += str(g & 1)
        binary_text += str(b & 1)
    binary_chunks = [binary_text[i:i + 8] for i in range(0, len(binary_text), 8)]
    secret_text = ""
    for chunk in binary_chunks:
        if chunk == '11111110':
            break
        secret_text += chr(int(chunk, 2))

    return secret_text

if __name__ == "__main__":
    while True:
        print("\nSpeed Steganography")
        print("1. Encode Text into Image")
        print("2. Decode Text from Image")
        print("3. Exit")
        choice = input("Choose an option: ")

        if choice == "1":
            image_path = input("Enter the path of the image: ")
            output_name = input("Enter the name for the new image (without extension): ")
            secret_text = input("Enter the text to hide: ")
            try:
                encode_text(image_path, output_name, secret_text)
            except Exception as e:
                print(f"Error: {e}")
        elif choice == "2":
            image_path = input("Enter the path of the encoded image: ")
            try:
                hidden_text = decode_text(image_path)
                print(f"Hidden text: {hidden_text}")
            except Exception as e:
                print(f"Error: {e}")
        elif choice == "3":
            print("Exiting the tool.")
            break
        else:
            print("Invalid option. Send a number from the list.")
