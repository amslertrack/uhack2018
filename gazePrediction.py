import numpy as np
import pandas as pd
import cv2
import time
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier


"""
cascade classifier from Haar Cascade data for facial and eye detection
"""
face_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_frontalface_alt.xml')
eye_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_eye.xml')


"""
machine learning from collected data
"""
data = pd.read_csv('gaze.txt', names=['ex', 'ey', 'ew', 'eh', 'gaze'])
x = data.drop('gaze', axis=1)
y = data['gaze']
rfc = RandomForestClassifier()
rfc.fit(x, y)


"""
prepare cv2 video capture
"""
cap = cv2.VideoCapture(0)

while(True):
	#capture frame-by-frame
	ret, frame = cap.read()
	gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

	"""
	detect serial of faces
	"""
	faces = face_cascade.detectMultiScale(gray)
	for (x, y, w, h) in faces:
		roi_gray = gray[y:y+h, x:x+w] #region of interest - gray
		roi_color = frame[y:y+h, x:x+w] #region of interest - color
		

		"""
		rectangle - face
		"""
		end_cord_x = x+w #right bottom x
		end_cord_y = y+h #right bottom y
		color = (255, 0, 0) #BGR
		stroke = 2 #thickness of border
		cv2.rectangle(frame, (x, y), (end_cord_x, end_cord_y), color, stroke)


		"""
		detect serial of eyes
		"""
		eyes = eye_cascade.detectMultiScale(roi_gray)

		count = 0
		x_test = np.array([])
		for (ex, ey, ew, eh) in eyes:
			"""
			rectangle - eyes
			"""
			end_cord_ex = ex+ew #right bottom x
			end_cord_ey = ey+eh #right bottom y
			color_eye = (0, 255, 0) #BGR
			stroke_eye = 2 #thickness of border
			cv2.rectangle(roi_color, (ex, ey), (ex+ew, ey+eh), color_eye, stroke_eye)
			#roi_eye = roi_gray[ey:ey+eh, ex:ex+ew]

			"""
			detect pupil
			"""
			#blur = cv2.GaussianBlur(roi_gray, (0,0), 2)
			circles = cv2.HoughCircles(roi_gray,cv2.HOUGH_GRADIENT,1,60,param1=200,param2=8,minRadius=9,maxRadius=20)
			
			"""
			circle - pupil
			"""
			try:
				for i in circles[0, :]:
					cv2.circle(roi_color, (i[0], i[1]), i[2], (255,255,255), 2)
					cv2.circle(roi_color, (i[0], i[1]), 2, (0,0,255), 3)
			except Exception as e:
				print(e)

			
			"""
			Gaze prediction from machine learning
			
			count += 1
			track_list = np.array([ex, ey, ew, eh])
			try:
				x_test = np.concatenate((x_test, track_list))
			except ValueError:
				break
			if count == 2:
				x_test.shape = (2,4)
				print(x_test)
				prediction = rfc.predict(x_test)
				print(prediction)
				count = 0
			"""


	#display the frames
	cv2.imshow('Face Recognition', frame)
	if cv2.waitKey(20) & 0xFF == ord('q'):
		break

cap.release
cv2.destroyAllWindows()
