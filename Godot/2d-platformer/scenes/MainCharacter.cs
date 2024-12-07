using System;
using Godot;
 
public partial class main_character : CharacterBody2D
{
	public const float Speed = 400.0f;
	public const float JumpVelocity = -900.0f;
 
	private AnimatedSprite2D sprite2d;
 
	public override void _Ready()
	{
		sprite2d = GetNode<AnimatedSprite2D>("Sprite2D");
		GD.Print(sprite2d);
	}
 
	// Get the gravity from the project settings to be synced with RigidBody nodes.
	public float gravity = ProjectSettings.GetSetting("physics/2d/default_gravity").AsSingle();
 
	
 
	public override void _PhysicsProcess(double delta)
	{
		Vector2 velocity = Velocity;
 
		// Animations
		if (Math.Abs(velocity.X) > 1)
			sprite2d.Animation = "running";
		else
			sprite2d.Animation = "default";
 
		// Add the gravity.
		if (!IsOnFloor()) {
			velocity.Y += gravity * (float)delta;
			sprite2d.Animation = "jumping";
		}
 
		// Handle Jump.
		if (Input.IsActionJustPressed("jump") && IsOnFloor())
			velocity.Y = JumpVelocity;
 
		// Get the input direction and handle the movement/deceleration.
		// As good practice, you should replace UI actions with custom gameplay actions.
		float direction = Input.GetAxis("left", "right");
		if (direction != 0)
		{
			velocity.X = direction * Speed;
		}
		else
		{
			velocity.X = Mathf.MoveToward(Velocity.X, 0, 12);
		}
 
		Velocity = velocity;
		MoveAndSlide();
 
		// Flip the sprite based on the direction.
		bool isLeft = velocity.X < 0;
		sprite2d.FlipH = isLeft;
	}
}
