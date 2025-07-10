
from flask import Flask, render_template, request
import subprocess

app = Flask(__name__)

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/run', methods=['POST'])
def run_cli():
    try:
        result = subprocess.run(
            ['java', 'HotelManagementSystem'],
            capture_output=True, text=True, cwd='/path/to/OOP'
        )
        output = result.stdout
    except Exception as e:
        output = f"Error running Java program: {e}"

    return render_template('index.html', output=output)

if __name__ == '__main__':
    app.run(debug=True)
