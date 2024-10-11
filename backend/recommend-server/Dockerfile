# Step 1: Use an official Python runtime as a parent image
FROM python:3.10-slim

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy the current directory contents into the container at /app
COPY . /app

# Step 4: Copy requirements.txt and install the Python dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir --upgrade -r requirements.txt

# Step 5: Expose the port that FastAPI will run on port
EXPOSE 5000

# Step 6: Command to run the FastAPI app with uvicorn
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "5000"]