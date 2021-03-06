package io.ray.runtime.task;

import io.ray.api.BaseActorHandle;
import io.ray.api.id.ActorId;
import io.ray.api.id.ObjectId;
import io.ray.api.id.PlacementGroupId;
import io.ray.api.options.ActorCreationOptions;
import io.ray.api.options.CallOptions;
import io.ray.api.options.PlacementGroupCreationOptions;
import io.ray.api.placementgroup.PlacementGroup;
import io.ray.runtime.functionmanager.FunctionDescriptor;
import java.util.List;

/** A set of methods to submit tasks and create actors. */
public interface TaskSubmitter {

  /**
   * Submit a normal task.
   *
   * @param functionDescriptor The remote function to execute.
   * @param args Arguments of this task.
   * @param numReturns Return object count.
   * @param options Options for this task.
   * @return Ids of the return objects.
   */
  List<ObjectId> submitTask(
      FunctionDescriptor functionDescriptor,
      List<FunctionArg> args,
      int numReturns,
      CallOptions options);

  /**
   * Create an actor.
   *
   * @param functionDescriptor The remote function that generates the actor object.
   * @param args Arguments of this task.
   * @param options Options for this actor creation task.
   * @return Handle to the actor.
   * @throws IllegalArgumentException if actor of specified name exists
   */
  BaseActorHandle createActor(
      FunctionDescriptor functionDescriptor, List<FunctionArg> args, ActorCreationOptions options)
      throws IllegalArgumentException;

  /**
   * Submit an actor task.
   *
   * @param actor Handle to the actor.
   * @param functionDescriptor The remote function to execute.
   * @param args Arguments of this task.
   * @param numReturns Return object count.
   * @param options Options for this task.
   * @return Ids of the return objects.
   */
  List<ObjectId> submitActorTask(
      BaseActorHandle actor,
      FunctionDescriptor functionDescriptor,
      List<FunctionArg> args,
      int numReturns,
      CallOptions options);

  /**
   * Create a placement group.
   *
   * @param creationOptions Creation options of the placement group.
   * @return A handle to the created placement group.
   */
  PlacementGroup createPlacementGroup(PlacementGroupCreationOptions creationOptions);

  /**
   * Remove a placement group by id.
   *
   * @param id Id of the placement group.
   */
  void removePlacementGroup(PlacementGroupId id);

  /**
   * Wait for the placement group to be ready within the specified time.
   *
   * @param id Id of placement group.
   * @param timeoutSeconds Timeout in seconds.
   * @return True if the placement group is created. False otherwise.
   */
  boolean waitPlacementGroupReady(PlacementGroupId id, int timeoutSeconds);

  BaseActorHandle getActor(ActorId actorId);
}
